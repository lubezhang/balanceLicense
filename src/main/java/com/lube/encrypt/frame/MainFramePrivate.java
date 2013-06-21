package com.lube.encrypt.frame;

import com.lube.encrypt.licenseCode.ActivateCode;
import com.lube.encrypt.licenseCode.AuthorizationCode;
import com.lube.encrypt.licenseCode.MakeSoftdog;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created with IntelliJ IDEA.
 * User: zhangqinghong
 * Date: 13-6-4
 * Time: 下午1:25
 * To change this template use File | Settings | File Templates.
 */
public class MainFramePrivate extends JFrame{
    private JTextArea activateCodeText;
    private JTextArea authorCodeText;
    private JTextArea message;

    public MainFramePrivate(){
        super();
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        this.setTitle("生成授权码");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        Container currPanel = this.getContentPane();
        currPanel.setLayout(new BoxLayout(currPanel,BoxLayout.Y_AXIS));

        currPanel.add(createButtonPanel());
        currPanel.add(createActivatePanel());
        currPanel.add(createAuthorPanel());
        currPanel.add(createMessagePanel());

        setSize(300,400);
        setLocation(250, 150);
        this.setResizable(false);
        pack();
        setVisible(true);
    }

    private JComponent createButtonPanel(){
        Box box1 = new Box(BoxLayout.X_AXIS);

        JButton activateBtn = new JButton("生成授权码");
        setSize(activateBtn);
        activateBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    String activateCode = activateCodeText.getText();
                    activateCode = activateCode.replaceAll("\n","");
                    AuthorizationCode authorizationCode = new AuthorizationCode();
                    authorCodeText.setText(authorizationCode.generateCode(activateCode));
                    message.append("生成授权码！" + "\n");
                } catch (Exception exp){
                    message.append(exp.getMessage()+"\n");
                }
            }
        });
        box1.add(activateBtn);
        return box1;
    }

    private JComponent createActivatePanel(){
        Box box1 = new Box(BoxLayout.X_AXIS);
        this.activateCodeText = new JTextArea();
        activateCodeText.setLineWrap(true);
//        activateCodeText.setEditable(false);
        setTextSize(activateCodeText);
        box1.add(this.activateCodeText);


        return box1;
    }

    private JComponent createAuthorPanel(){
        Box box1 = new Box(BoxLayout.X_AXIS);
        this.authorCodeText = new JTextArea();
        authorCodeText.setLineWrap(true);

        setTextSize(authorCodeText);
        box1.add(this.authorCodeText);


        return box1;
    }

    private JComponent createMessagePanel(){
        Box box3 = new Box(BoxLayout.X_AXIS);
        message = new JTextArea(10,4);
        message.setLineWrap(true);
        message.setWrapStyleWord(true);
        message.setEditable(false);
        box3.add(new JScrollPane(message));
        return box3;
    }

    private void setSize(JComponent comp) {
        comp.setPreferredSize(new Dimension(100, 25));//设置最大、最小和合适的大小相同
        comp.setMaximumSize(new Dimension(100, 25));
        comp.setMinimumSize(new Dimension(100, 25));
//        comp.setFont(new Font(Font.MONOSPACED, Font.BOLD, 12));
    }

    private void setTextSize(JComponent comp){
        comp.setPreferredSize(new Dimension(620, 60));//设置最大、最小和合适的大小相同
        comp.setMaximumSize(new Dimension(620, 60));
        comp.setMinimumSize(new Dimension(620, 60));
        comp.setBorder(new LineBorder(new Color(122,139,153)));
    }

    public static void main(String[] args){
        new MainFramePrivate();
    }
}
