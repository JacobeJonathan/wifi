import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.NetworkInterface;
import java.net.SocketException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;

public class java extends JFrame implements ActionListener {
    private static final long serialVersionUID = 1L;
    public static final String ACCOUNT_SID = "AC73570b594e6863d6354224c074070845";
    public static final String AUTH_TOKEN = "7a07b1a18cca2595e2f96a6394236978";
    public static final String FROM_NUMBER = "+15074362687";
    public static final String TO_NUMBER = "+51920388169";

    private JLabel statusLabel;

    public java() { //java es nombre del documento java
        setTitle("WiFi Checker with Notification");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 150);
        setLocationRelativeTo(null);

        JButton checkWifiButton = new JButton("Check WiFi");
        checkWifiButton.addActionListener(this);

        JButton notifyButton = new JButton("Notify");
        notifyButton.addActionListener(this);

        statusLabel = new JLabel(" ");

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout());
        buttonPanel.add(checkWifiButton);
        buttonPanel.add(notifyButton);

        JPanel statusPanel = new JPanel();
        statusPanel.setLayout(new BorderLayout());
        statusPanel.add(statusLabel, BorderLayout.CENTER);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());
        mainPanel.add(buttonPanel, BorderLayout.NORTH);
        mainPanel.add(statusPanel, BorderLayout.CENTER);

        setContentPane(mainPanel);
        setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("Check WiFi")) {
            try {
                NetworkInterface wifiInterface = NetworkInterface.getByName("ENTEL HOGAR"); // Reemplaza "wlan0" por el nombre de tu interfaz WiFi
                if (wifiInterface != null && wifiInterface.isUp()) {
                    statusLabel.setText("Estás conectado al WiFi");
                } else {
                    statusLabel.setText("No estás conectado al WiFi");
                }
            } catch (SocketException ex) {
                statusLabel.setText("Error al obtener la interfaz de red: " + ex.getMessage());
            }
        } else if (e.getActionCommand().equals("Notify")) {
            sendSMS(TO_NUMBER, "No estoy conectado al WiFi");
            statusLabel.setText("Mensaje enviado a " + TO_NUMBER);
        }
    }

    public static void sendSMS(String toNumber, String message) {
        Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
        Message twilioMessage = Message.creator(new PhoneNumber(toNumber), new PhoneNumber(FROM_NUMBER), message).create();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new java();
            }
        });
    }
}
