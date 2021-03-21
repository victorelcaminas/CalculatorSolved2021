import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormatSymbols;
import java.util.Locale;

public class Calculator extends JFrame {

    private enum OperatorType {NONE, ADD, SUBTRACT, MULTIPLY, DIVIDE}

    private JButton button1;
    private JButton button2;
    private JButton button3;
    private JButton button4;
    private JButton button5;
    private JButton button6;
    private JButton button7;
    private JButton buttonAdd;
    private JTextField textFieldDisplay;
    private JButton buttonSubtract;
    private JButton button8;
    private JButton button9;
    private JButton buttonMult;
    private JButton buttonComma;
    private JButton button0;
    private JButton buttonC;
    private JButton buttonDiv;
    private JButton buttonEqual;
    private JButton buttonBack;
    private JPanel mainPanel;

    private double accumulator, operand;
    private OperatorType operator;
    private char decimalSeparator;
    private boolean erase;

    public Calculator(String title) {
        super(title);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setContentPane(mainPanel);
        pack();

        initComponents();
        initMyFields();
    }

    class NumberButtonActionListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            JButton button = (JButton) e.getSource();
            String numberStr = button.getText();
            eraseIfNeededAndWriteNumber(numberStr);
        }
    }

    private void initComponents() {
        ActionListener numberListener = new NumberButtonActionListener();
        button0.addActionListener(numberListener);
        button1.addActionListener(numberListener);
        button2.addActionListener(numberListener);
        button3.addActionListener(numberListener);
        button4.addActionListener(numberListener);
        button5.addActionListener(numberListener);
        button6.addActionListener(numberListener);
        button7.addActionListener(numberListener);
        button8.addActionListener(numberListener);
        button9.addActionListener(numberListener);
        buttonComma.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String s = textFieldDisplay.getText();
                if ( ! s.contains("" + decimalSeparator)) {
                    eraseIfNeededAndWriteNumber("" + decimalSeparator);
                }
            }
        });
        buttonC.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                initMyFields();
                textFieldDisplay.setText("");
            }
        });
        buttonAdd.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!erase) {
                    erase = true;
                    calculateResult();
                    displayResult();
                    operator = OperatorType.ADD;
                }
            }
        });
        buttonSubtract.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!erase) {
                    erase = true;
                    calculateResult();
                    displayResult();
                    operator = OperatorType.SUBTRACT;
                }
            }
        });
        buttonMult.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!erase) {
                    erase = true;
                    calculateResult();
                    displayResult();
                    operator = OperatorType.MULTIPLY;
                }
            }
        });
        buttonDiv.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!erase) {
                    erase = true;
                    calculateResult();
                    displayResult();
                    operator = OperatorType.DIVIDE;
                }
            }
        });
        buttonEqual.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!erase) {
                    //erase = true;
                    calculateResult();
                    displayResult();
                    operator = OperatorType.NONE;
                }
            }
        });
        buttonBack.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String s = textFieldDisplay.getText();
                if (s.length()>0) {
                    String subS = s.substring(0, s.length()-1);
                    textFieldDisplay.setText(subS);
                }
            }
        });
    }

    public void initMyFields() {
        accumulator = 0;
        operand = 0;
        operator = OperatorType.NONE;
        erase = false;
        decimalSeparator = getDedicimalSeparator();

    }

    private void eraseIfNeededAndWriteNumber(String numberStr) {
        if (erase) {
            textFieldDisplay.setText("");
            erase = false;
        }
        textFieldDisplay.setText(textFieldDisplay.getText() +
                numberStr);
    }

    public char getDedicimalSeparator() {
        DecimalFormatSymbols dfs = new DecimalFormatSymbols(
                Locale.getDefault() );
        return dfs.getDecimalSeparator();
    }

    private void calculateResult() {
        String s = textFieldDisplay.getText();
        if (s.isEmpty()) {
            s = "0";
        }
        s = s.replaceAll("\\"+decimalSeparator, ".");
        operand = Double.parseDouble(s);
        switch(operator) {
            case ADD:
                accumulator += operand;
                break;
            case SUBTRACT:
                accumulator -= operand;
                break;
            case MULTIPLY:
                accumulator *= operand;
                break;
            case DIVIDE:
                accumulator /= operand;
                break;
            case NONE:
                accumulator = operand;
                break;
        }
    }

    private void displayResult() {
        String s = ""+accumulator;
        if (s.contains(".")) {
            s = s.replaceAll("0+$", "");
            s = s.replaceAll("\\.$", "");
        }
        s = s.replaceAll("\\.", ""+decimalSeparator);
        textFieldDisplay.setText(s);
        //BigDecimal number = new BigDecimal(accumulator);
        //textFieldDisplay.setText(number.stripTrailingZeros().toPlainString());
    }


    public static void main(String args[]) {
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                JFrame frame = new Calculator("Converter");
                frame.setVisible(true);
            }
        });
    }
}
