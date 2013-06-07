package com.lube.encrypt.frame;

import com.lube.encrypt.licenseCode.ActivateCode;
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
public class MainFrame extends JFrame{
    private JTextArea activateCodeText;
    private JTextArea authorCodeText;
    private JTextArea message;

    public MainFrame(){
        super();
        this.setTitle("制作加密狗");
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

        JButton activateBtn = new JButton("activateCode");
        setSize(activateBtn);
        activateBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    ActivateCode usbKey = new ActivateCode();
                    String activateCode = usbKey.generateCode();
                    activateCode = activateCode.replaceAll("\n","");
                    activateCodeText.setText(activateCode);
                    message.append("生成激活码！" + "\n");
                } catch (Exception exp){
                    message.append(exp.getMessage()+"\n");
                }

            }
        });
        box1.add(activateBtn);

        JButton makeBtn = new JButton("make");
        setSize(makeBtn);
        makeBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    MakeSoftdog softdog = new MakeSoftdog();
                    softdog.make(authorCodeText.getText());
                    message.append("制作加密狗成功！" + "\n");
                } catch (Exception exp) {
                    message.append(exp.getMessage() + "\n");
                }
            }
        });
        box1.add(makeBtn);

        JButton reset = new JButton("reset");
        setSize(reset);
        reset.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    MakeSoftdog softdog = new MakeSoftdog();
                    softdog.resetUserPin(authorCodeText.getText());
                    message.append("重置用户信息成功！" + "\n");
                } catch (Exception exp){
                    message.append(exp.getMessage() + "\n");
                }
            }
        });
        box1.add(reset);

        return box1;
    }

    private JComponent createActivatePanel(){
        Box box1 = new Box(BoxLayout.X_AXIS);
        this.activateCodeText = new JTextArea();
        activateCodeText.setLineWrap(true);
        activateCodeText.setEditable(false);
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
        box3.add(new JScrollPane(message));
        return box3;
    }

    private void setSize(JComponent comp) {
        comp.setPreferredSize(new Dimension(90, 25));//设置最大、最小和合适的大小相同
        comp.setMaximumSize(new Dimension(90, 25));
        comp.setMinimumSize(new Dimension(90, 25));
    }

    private void setTextSize(JComponent comp){
        comp.setPreferredSize(new Dimension(450, 55));//设置最大、最小和合适的大小相同
        comp.setMaximumSize(new Dimension(450, 55));
        comp.setMinimumSize(new Dimension(450, 55));
        comp.setBorder(new LineBorder(new Color(122,139,153)));
    }

    public static void main(String[] args){
        new MainFrame();
    }
}
