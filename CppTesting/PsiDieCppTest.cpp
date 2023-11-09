#include <iostream>
#include <iomanip>
#include <ctime>
#include <algorithm>
short totalNumOfRolls, numOfRolls_d20, numOfRolls_d12, numOfRolls_d10, numOfRolls_d8, numOfRolls_d6, numOfRolls_d4, numOfRolls_d2, result, pointsMade, lowestNumOfRolls, highestNumOfRolls;
const int testRuns = 2'000'000;
short totalRolls[testRuns], totalRolls_d20[testRuns], totalRolls_d12[testRuns], totalRolls_d10[testRuns], totalRolls_d8[testRuns], totalRolls_d6[testRuns], totalRolls_d4[testRuns], totalRolls_d2[testRuns], points[testRuns];
double trimAmount = 8;
char currentFocusPoints = 1, initialDieSize = 8, psiDie = initialDieSize;
bool includePoints = false, increaseDieSize = false, include_d20 = false, include_d12 = false, include_d10 = false, include_d8 = true, include_d6 = true, include_d4 = true, include_d2 = true;

void rollDice(bool includeLowerDie, bool includeHigherDie){
    result = rand() % psiDie + 1;
    pointsMade += result;
    totalNumOfRolls++;
    if(result <= currentFocusPoints && includeLowerDie) {
            psiDie -= 2;
            if(psiDie == 18) psiDie = 12;
            else if(psiDie == 2 && !include_d2) psiDie = 0;
    }
    else if(result == psiDie && includeHigherDie && increaseDieSize) {
        psiDie += 2;
        if (psiDie == 14) psiDie = 20;
    }
}

void testDice(){
    for(int i = 0; i < testRuns; i++){
        psiDie = initialDieSize;
        do{ switch (psiDie){
            case 20:
                numOfRolls_d20++;
                rollDice(include_d12, false);
                break;
            case 12: 
                numOfRolls_d12++;
                rollDice(include_d10, include_d20);
                break;
            case 10:
                numOfRolls_d10++;
                rollDice(include_d8, include_d12);
                break;
            case 8:
                numOfRolls_d8++;
                rollDice(include_d6, include_d10);
                break;
            case 6:
                numOfRolls_d6++;
                rollDice(include_d4, include_d8);
                break;
            case 4:
                numOfRolls_d4++;
                rollDice(true, include_d6);
                break;
            case 2:
                numOfRolls_d2++;
                rollDice(true, include_d4);
                break;
            default:
                std::cout << psiDie << '\n';
                break;
        } }while (psiDie > 0);
        totalRolls[i] = totalNumOfRolls;
        if (include_d20) totalRolls_d20[i] = numOfRolls_d20;
        if (include_d12) totalRolls_d12[i] = numOfRolls_d12;
        if (include_d10) totalRolls_d10[i] = numOfRolls_d10;
        if (include_d8) totalRolls_d8[i] = numOfRolls_d8;
        if (include_d6) totalRolls_d6[i] = numOfRolls_d6;
        if (include_d4) totalRolls_d4[i] = numOfRolls_d4;
        if (include_d2) totalRolls_d2[i] = numOfRolls_d2;
        if (includePoints) points[i] = pointsMade;
        if (totalNumOfRolls < lowestNumOfRolls) lowestNumOfRolls = totalNumOfRolls;
        else if ( totalNumOfRolls > highestNumOfRolls) highestNumOfRolls = totalNumOfRolls;
        totalNumOfRolls = pointsMade = numOfRolls_d20 = numOfRolls_d12 = numOfRolls_d10 = numOfRolls_d8 = numOfRolls_d6 = numOfRolls_d4 = numOfRolls_d2 = 0;
        int test = 0;
        test++;
    }
}

double getArrayAverage(short array[]){
    double sum = 0;
    for(int i = 0; i < testRuns; i++){
        sum += array[i];
    }
    return sum / (double)testRuns;
}

void winsorizeArray(short array[]){
    std::sort(array, array + testRuns);
    int trimmedLength = testRuns * (trimAmount / 100);
    int shortenedTrim = trimAmount / 20;
    for(int i = 0; i < (testRuns*trimAmount)/100; i++){
        // array[i] = array[static_cast<int>((testRuns*trimAmount)/100)];
        if(i < shortenedTrim){array[i] = array[shortenedTrim];}
        array[testRuns - i - 1] = array[testRuns - trimmedLength - 1];
    }
}

void processResults(short array[], std::string name){
    winsorizeArray(array);
    double arrayAverage = getArrayAverage(array);
    if (name == "Total" || name == "Points") {
        std::cout << "-----" << name << " Rolls -----" << '\n';
        std::cout << "Lowest: " << array[0] << '\n';
        std::cout << "Highest: " << array[testRuns - 1] << '\n';
    } else {
        double totalAverage = getArrayAverage(totalRolls);
        std::cout << "-------" << name << " Rolls -----" << '\n';
        std::cout << "Percentage: " << std::fixed << std::setprecision(2) << (arrayAverage / totalAverage) * 100 << "%" << '\n';
    }
    std::cout << "Mean: " << std::fixed << std::setprecision(2) << arrayAverage << '\n';
    std::cout << "Median: " << std::fixed << std::setprecision(2) << array[testRuns / 2] << '\n';
}

//Why is this slower than Java?
int main(){
    clock_t startTime = clock();
    if(initialDieSize > 12 && !include_d20) initialDieSize = 12; //Makes sure the initial die size is valid
    if(initialDieSize > 10 && !include_d12) initialDieSize = 10;
    if(initialDieSize > 8 && !include_d10) initialDieSize = 8;
    if(initialDieSize > 6 && !include_d8) initialDieSize = 6;
    if(initialDieSize > 4 && !include_d6) initialDieSize = 4;
    if(initialDieSize > 2 && !include_d4) initialDieSize = 2;
    testDice();

    if(include_d2) processResults(totalRolls_d2, "d2");
    if(include_d4) processResults(totalRolls_d4, "d4");
    if(include_d6) processResults(totalRolls_d6, "d6");
    if(include_d8) processResults(totalRolls_d8, "d8");
    if(include_d10) processResults(totalRolls_d10, "d10");
    if(include_d12) processResults(totalRolls_d12, "d12");
    if(include_d20) processResults(totalRolls_d20, "d20");
    processResults(totalRolls, "Total");
    if(includePoints) processResults(points, "Points");
    
    std::cout << std::fixed << double(clock() - startTime) / double(CLOCKS_PER_SEC) << std::setprecision(5); 
    return 0;
}