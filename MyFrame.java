import javax.swing.*;
import java.awt.*;

public class MyFrame extends JFrame{
	MyFrame(){
		JButton button = new JButton("Click me");
		button.setBounds(100, 100, 100, 50);
		button.addActionListener(e -> System.out.println("Hello"));
		button.setFocusable(false);
/*
		Border border = BorderFactory.createLineBorder(Color.black, 3); //make a border
		JPanel redPanel = new JPanel();
		redPanel.setBackground(Color.red);
		redPanel.setBounds(0, 0, 250, 100);
		redPanel.setLayout(new BorderLayout());
*/
		JLabel label = new JLabel(); //make a label
		label.setText("PsiDie"); //set the text of the label
		label.setForeground(Color.white); //set the color of the text
		label.setFont(new Font("Arial", Font.PLAIN, 18)); //set the font of the text
//		label.setBorder(border); //set the border of the label
		label.setVerticalAlignment(JLabel.TOP); //set the vertical position of the text
		label.setBounds(10, 10, 60, 30); //set the position and size of the label

		this.setSize(500, 400); //set size of frame
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //make the program exit when the frame is closed
		this.setResizable(false); //make the frame not resizable
		this.setVisible(true); //make the frame visible
		this.getContentPane().setBackground(new Color(0x123456)); //set the background color of the frame
		this.add(label); //add the label to the frame
		this.setLayout(null); //set the layout of the frame to null
		this.setVisible(true); //make the frame visible
		this.add(button);
//		redPanel.add(label); //add the label to panel
//		this.add(redPanel);
	}
}
