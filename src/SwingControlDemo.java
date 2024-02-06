import java.awt.*;
import java.awt.event.*;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import javax.swing.*;

public class SwingControlDemo implements ActionListener {
    private JFrame mainFrame;
    private JLabel headerLabel;
    private JLabel statusLabel;
    private JLabel titleLabel;
    private JPanel inputPanel;
    private JPanel keywordPanel;
    private JMenuBar mb;
    private JMenu file, edit, help;
    private JMenuItem cut, copy, paste, selectAll;
    private JTextArea results;
    private JTextArea urlTextArea;
    private JTextArea wordTextArea;
    private int WIDTH=400;
    private int HEIGHT=500;


    public SwingControlDemo() {
        prepareGUI();
     //   mainMethod();
    }

    public void mainMethod(){
        String key = wordTextArea.getText();

        try{
            URL url = new URL(urlTextArea.getText());

           //URL url = new URL("https://www.milton.edu/");

            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(url.openStream()));
            String line;
            while ((line = reader.readLine()) != null) {
                if(line.contains("href=")){
                    //System.out.println(line);
                    int start = line.indexOf("href=");
                    int end = line.indexOf("\"", start+7);
                    int send = line.indexOf("\'", start+7);
                    // System.out.println(send);
                    //  System.out.println(end);
                    if(send<0){
                        if(line.substring(start+6, end).contains(key)){
                            System.out.println(line.substring(start+6, end));
                            results.append(line.substring(start+6, end)+"\n");}
                    } else if (end<0) {
                        if(line.substring(start+6,send).contains(key)){
                            System.out.println(line.substring(start+6,send));
                            results.append(line.substring(start+6,send)+"\n");
                        }
                    } else if (send<end) {
                        if(line.substring(start+6,send).contains(key)){
                            System.out.println(line.substring(start+6,send));
                            results.append(line.substring(start+6,send)+"\n");
                        }
                    }else{
                        if(line.substring(start+6,end).contains(key)){
                            System.out.println(line.substring(start+6,end));
                            results.append(line.substring(start+6,end)+"\n");
                        }
                    }

                }

            }
            reader.close();
        }catch(Exception ex){
            System.out.println(ex);
        }

    }

    public static void main(String[] args) {
        SwingControlDemo swingControlDemo = new SwingControlDemo();
        swingControlDemo.showEventDemo();
    }

    private void prepareGUI() {
        mainFrame = new JFrame("URL Looker Upper");
        mainFrame.setSize(WIDTH, HEIGHT);
        mainFrame.setLayout(new BorderLayout());


        cut = new JMenuItem("cut");
        copy = new JMenuItem("copy");
        paste = new JMenuItem("paste");
        selectAll = new JMenuItem("selectAll");
        cut.addActionListener(this);
        copy.addActionListener(this);
        paste.addActionListener(this);
        selectAll.addActionListener(this);

        mb = new JMenuBar();
        file = new JMenu("File");
        edit = new JMenu("Edit");
        help = new JMenu("Help");
        edit.add(cut);
        edit.add(copy);
        edit.add(paste);
        edit.add(selectAll);
        mb.add(file);
        mb.add(edit);
        mb.add(help);


        results = new JTextArea("Results:");
        results.setBounds(50, 5, WIDTH-100, HEIGHT-50);
        mainFrame.add(mb);

        JScrollPane scrollPane = new JScrollPane(results);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        mainFrame.setJMenuBar(mb);

        headerLabel = new JLabel("", JLabel.CENTER);
        statusLabel = new JLabel("", JLabel.CENTER);
        statusLabel.setSize(350, 100);

        mainFrame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent windowEvent) {
                System.exit(0);
            }
        });



        inputPanel = new JPanel();
        inputPanel.setLayout(new BorderLayout());
        urlTextArea = new JTextArea("URL:");
        urlTextArea.setBounds(50, 5, WIDTH-100, HEIGHT-50);

        inputPanel.add(urlTextArea, BorderLayout.NORTH);



        keywordPanel = new JPanel();
        keywordPanel.setLayout(new BorderLayout());
        wordTextArea = new JTextArea("Keyword:");
        wordTextArea.setBounds(50, 5, WIDTH-100, HEIGHT-50);

        keywordPanel.add(wordTextArea, BorderLayout.NORTH);
        inputPanel.add(keywordPanel, BorderLayout.CENTER);

        mainFrame.add(inputPanel, BorderLayout.NORTH);
        mainFrame.add(scrollPane, BorderLayout.CENTER);

        mainFrame.setVisible(true);
    }

    private void showEventDemo() {
        headerLabel.setText("Control in action: Button");


        JButton submitButton = new JButton("Submit");



        submitButton.setActionCommand("Submit");



        submitButton.addActionListener(new ButtonClickListener());



        keywordPanel.add(submitButton, BorderLayout.CENTER);


        mainFrame.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == cut)
            results.cut();
        if (e.getSource() == paste)
            results.paste();
        if (e.getSource() == copy)
            results.copy();
        if (e.getSource() == selectAll)
            results.selectAll();
    }

    private class ButtonClickListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            String command = e.getActionCommand();

            if (command.equals("Submit")) {
                results.setText("Please Fill out ALL information Sections");
                mainMethod();
            }
        }
    }
}