package forms;

import javax.swing.*;

public class form {
    public static void main(String[] args) {
        JFrame frame = new JFrame("form");
        frame.setContentPane(new form().panel1);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }

    private JPanel panel1;
    private JButton button1;
    private JButton button2;
    private JTextArea textArea1;
    private JButton button3;
    private JTextArea textArea2;
    private JTabbedPane tabbedPane1;
    private JTextField textField1;
    private JTextField textField2;
    private JTextField textField6;
    private JTextField textField5;
    private JTextField textField7;
    private JTextField textField3;
    private JTable table1;
    private JEditorPane editorPane1;
    private JTextPane textPane1;
    private JButton saveButton;
    private JButton clearButton;
    private JPanel panel2;
    private JPanel panel3;

    private void createUIComponents() {
        // TODO: place custom component creation code here
    }
}
