package dev.tdminhnhat.gui;

import dev.tdminhnhat.entity.DatabaseInformation;
import dev.tdminhnhat.enums.TypeDatabase;
import dev.tdminhnhat.service.GenerateDatabaseService;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

class UserControlPanel extends JPanel implements ActionListener {

    private final JComboBox<TypeDatabase> cbChooseTypeDatabase = new JComboBox<>(TypeDatabase.values());
    private final JTextField txtInputHost = new JTextField("localhost");
    private final JTextField txtInputPort = new JTextField("1433");
    private final JTextField txtInputDatabaseName = new JTextField("");
    private final JTextField txtInputUsername = new JTextField("sa");
    private final JPasswordField pwdInputPassword = new JPasswordField("123456789");
    private final JButton btnGenerate = new JButton("Generate Database");
    private final JButton btnTestConnect = new JButton("Test Connect");
    private final JButton btnClearInput = new JButton("Clear Input");
    private final JButton btnExportClass = new JButton("Export Class");

    public UserControlPanel() {
        this.setLayout(new BorderLayout());
        Box box = new Box(BoxLayout.Y_AXIS);

        // Row 1: Title of Panel
        JPanel row1 = new JPanel();
        row1.setLayout(new BoxLayout(row1, BoxLayout.X_AXIS));
        JLabel title = new JLabel("Input Database Connection Information", JLabel.CENTER);
        title.setFont(new Font("Comic Sans MS", Font.BOLD, 30));
        row1.add(title);

        // Row 2: Input host, port and choose type database
        JPanel row2 = new JPanel();
        row2.setLayout(new BoxLayout(row2, BoxLayout.X_AXIS));
        JLabel lbInputHost = new JLabel("Host:");
        lbInputHost.setFont(new Font("", Font.PLAIN, 15));
        lbInputHost.setPreferredSize(new Dimension(50, 30));
        row2.add(lbInputHost);
        row2.add(txtInputHost);
        JLabel lbInputPort = new JLabel("Port:");
        lbInputPort.setFont(new Font("", Font.PLAIN, 15));
        lbInputPort.setPreferredSize(new Dimension(50, 30));
        row2.add(lbInputPort);
        row2.add(txtInputPort);
        JLabel lbChooseTypeDatabase = new JLabel("Type Database:");
        lbChooseTypeDatabase.setFont(new Font("", Font.PLAIN, 15));
        lbChooseTypeDatabase.setPreferredSize(new Dimension(150, 30));
        row2.add(lbChooseTypeDatabase);
        row2.add(cbChooseTypeDatabase);

        // Row 3: Input database name, username and password
        JPanel row3 = new JPanel();
        row3.setLayout(new BoxLayout(row3, BoxLayout.X_AXIS));
        JLabel lbInputDatabaseName = new JLabel("Database Name:");
        lbInputDatabaseName.setFont(new Font("", Font.PLAIN, 15));
        lbInputDatabaseName.setPreferredSize(new Dimension(150, 30));
        row3.add(lbInputDatabaseName);
        row3.add(txtInputDatabaseName);
        JLabel lbInputUsername = new JLabel("Username:");
        lbInputUsername.setFont(new Font("", Font.PLAIN, 15));
        lbInputUsername.setPreferredSize(new Dimension(100, 30));
        row3.add(lbInputUsername);
        row3.add(txtInputUsername);
        JLabel lbInputPassword = new JLabel("Password:");
        lbInputPassword.setFont(new Font("", Font.PLAIN, 15));
        lbInputPassword.setPreferredSize(new Dimension(100, 30));
        row3.add(lbInputPassword);
        row3.add(pwdInputPassword);
        row2.add(Box.createRigidArea(new Dimension(10, 0)));
        row2.add(row3);

        // Row 4: Button Generate, Test Connect, Clear Input and Export Class
        JPanel row4 = new JPanel();
        row4.setLayout(new BoxLayout(row4, BoxLayout.X_AXIS));
        row4.add(btnGenerate);
        row4.add(Box.createRigidArea(new Dimension(10, 0)));
        row4.add(btnTestConnect);
        row4.add(Box.createRigidArea(new Dimension(10, 0)));
        row4.add(btnClearInput);
        row4.add(Box.createRigidArea(new Dimension(10, 0)));
        row4.add(btnExportClass);

        box.add(row1);
        box.add(row2);
        box.add(row3);
        box.add(row4);
        this.add(box, BorderLayout.CENTER);
        addEvent();
    }

    private void addEvent() {
        cbChooseTypeDatabase.addActionListener(this);
        btnGenerate.addActionListener(this);
        btnTestConnect.addActionListener(this);
        btnClearInput.addActionListener(this);
        btnExportClass.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() instanceof JComboBox<?>) {
            JComboBox<TypeDatabase> target = ((JComboBox<TypeDatabase>) e.getSource());
            txtInputPort.setText(((TypeDatabase) target.getSelectedItem()).getPort().toString());
        } else {
            switch(e.getActionCommand()) {
                case "Generate Database" -> {
                    if(checkInputEmpty()) {
                        toggleActionButton(false);
                        new Thread(() -> {

                            toggleActionButton(true);
                        }).start();
                    }
                }
                case "Test Connect" -> {
                    if(checkInputEmpty()) {
                        toggleActionButton(false);
                        new Thread(() -> {
                            DatabaseInformation databaseInformation = new DatabaseInformation(
                                    txtInputHost.getText(),
                                    Integer.parseInt(txtInputPort.getText()),
                                    txtInputUsername.getText(),
                                    String.valueOf(pwdInputPassword.getPassword()),
                                    txtInputDatabaseName.getText(),
                                    ((TypeDatabase) cbChooseTypeDatabase.getSelectedItem()),
                                    null
                            );
                            new GenerateDatabaseService().testConnection(databaseInformation);
                            toggleActionButton(true);
                        }).start();
                    }
                }
                case "Clear Input" -> {
                    txtInputHost.setText("localhost");
                    txtInputPort.setText("1433");
                    txtInputDatabaseName.setText("");
                    txtInputUsername.setText("sa");
                    pwdInputPassword.setText("123456789");
                    cbChooseTypeDatabase.setSelectedIndex(0);
                    txtInputHost.setFocusable(true);
                }
                case "Export Class" -> {

                }
            }
        }
    }

    private void toggleActionButton(boolean value) {
        btnTestConnect.setEnabled(value);
        btnClearInput.setEnabled(value);
        btnGenerate.setEnabled(value);
        btnExportClass.setEnabled(value);
    }

    private void showMessageError(String message) {
        JOptionPane.showMessageDialog(null, message, "Error",  JOptionPane.ERROR_MESSAGE);
    }

    private boolean checkInputEmpty() {
        if(txtInputHost.getText().isEmpty()) {
            showMessageError("Please enter a valid Host");
            return false;
        } else if(txtInputPort.getText().isEmpty()) {
            showMessageError("Please enter a valid Port");
            return false;
        } else if(txtInputUsername.getText().isEmpty()) {
            showMessageError("Please enter a valid Username");
            return false;
        } else if(pwdInputPassword.getPassword().length == 0) {
            showMessageError("Please enter a valid Password");
            return false;
        } else if(txtInputDatabaseName.getText().isEmpty()) {
            showMessageError("Please enter a valid Database Name");
            return false;
        }
        return true;
    }
}
