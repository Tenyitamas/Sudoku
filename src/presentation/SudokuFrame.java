package presentation;

import domain.model.Field;
import domain.model.GameState;
import domain.model.Level;
import domain.model.SudokuGame;
import util.Constants;
import util.TimeUtil;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import static util.Constants.GRID_SIZE;
import static util.Constants.GRID_SIZE_SQUARE_ROOT;

public class SudokuFrame extends JFrame implements WindowListener {

    private static final ImageIcon DELETE_IMAGE_ICON = new ImageIcon("res/icons8-trash-24.png");
    static JMenuBar menuBar;

    static JMenu gameMenu;
    static JMenuItem newGameMenuItem;
    static JMenuItem checkForMistakesMenuItem;

    static JMenu levelMenu;
    static JMenuItem easyMenuItem;
    static JMenuItem mediumMenuItem;
    static JMenuItem hardMenuItem;


    static JButton[][] cells = new JButton[GRID_SIZE][GRID_SIZE];

    static JLabel nextGameInfoLabel;
    static JLabel currentGameInfoLabel;
    static JLabel selectedValueInfoLabel;
    static JLabel timeLabel;

    static JPanel content;
    static JPanel boardPanel;
    static JPanel infoLabelsPanel;
    static JPanel numbersPanel;

    static Timer timer;

    static GridBagLayout sudokuLayout;


    private final SudokuFrameListener listener;
    private int selectedNum = 0;


    public SudokuFrame(SudokuFrameListener listener) {
        this.listener = listener;
        addWindowListener(this);

        setTitle("Sudoku");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        initMenuBar();
        initInfoLabels();
        initTimer();
        initPanels();

        setSize(1000, 1000);
        setResizable(false);


    }

    private void initTimer() {
        timer = new Timer(1000, e -> {
            listener.onSecondPassed();
        });
        timer.start();
    }


    public void updateViewWithGame(SudokuGame game) {
        fillBoard(game.getFields());
        setTextForCurrentGameLevel(game.getLevel());
        numbersPanel.setVisible(true);

        if(game.getGameState() == GameState.ONGOING) {
            if(!timer.isRunning()) {
                timer.start();
            }

        } else if(game.getGameState() == GameState.COMPLETE) {
            timer.stop();
            numbersPanel.setVisible(false);
            setTextForTimeLabel(game.getTimeElapsedInSec());
            showMessageDialog("Congratulations, You won!");
        }
    }


    public void showMessageDialog(String message) {
        JOptionPane.showMessageDialog(this, message);
    }

    private void initPanels() {
        getContentPane().removeAll();

        boardPanel = createBoardPanel();
        numbersPanel = createNumbersPanel();
        infoLabelsPanel = createInfoLabelsPanel();


        content = new JPanel(new BorderLayout()) {{
            add(infoLabelsPanel, BorderLayout.NORTH);

            add(boardPanel, BorderLayout.CENTER);

            add(numbersPanel, BorderLayout.SOUTH);
        }};

        getContentPane().add(content);
    }

    private JPanel createInfoLabelsPanel() {
        return new JPanel(new GridLayout(3, 1)) {{
            add(nextGameInfoLabel);
            add(currentGameInfoLabel);
            add(selectedValueInfoLabel);
            add(timeLabel);
            setPreferredSize(new Dimension(1000, 100));
        }};
    }

    private JPanel createNumbersPanel() {
        return new JPanel(new FlowLayout(FlowLayout.CENTER)) {{
            add(new JButton(DELETE_IMAGE_ICON) {{
                addActionListener(e -> onNumberClicked(0));
            }});
            for (int i = Constants.MIN_DIGIT_VALUE; i <= Constants.MAX_DIGIT_VALUE; i++) {
                add(new JButton(i + "") {{
                    addActionListener(e -> onNumberClicked(Integer.parseInt(this.getText())));
                }});
            }
            setPreferredSize(new Dimension(1000, 100));
        }};
    }

    private JPanel createBoardPanel() {
        return new JPanel(new GridLayout(GRID_SIZE, GRID_SIZE, 0, 0)) {{
            for (int i = 0; i < GRID_SIZE; i++) {
                for (int j = 0; j < GRID_SIZE; j++) {
                    int row = i;
                    int col = j;

                    int top = 1;
                    int bottom = 1;
                    int left = 1;
                    int right = 1;
                    Color borderColor = Color.BLACK;

                    if(i % GRID_SIZE_SQUARE_ROOT == 0) top += 2;
                    if(j % GRID_SIZE_SQUARE_ROOT == 0) left += 2;
                    if(i == 0) top += 2;
                    if(j == 0) left += 2;
                    if(i == GRID_SIZE - 1) bottom += 4;
                    if(j == GRID_SIZE - 1) right += 4;

                    cells[i][j] = new JButton("shd") {{
                        addActionListener(e -> onFieldClickedAtLocation(row, col));
                        setBackground(Color.WHITE);
                        setFont(new Font(Font.DIALOG, Font.BOLD, 20));
                    }};

                    cells[i][j].setBorder(BorderFactory.createMatteBorder(top, left, bottom, right, borderColor));
                    add(cells[i][j]);
                }
            }
        }};
    }

    private void initInfoLabels() {
        nextGameInfoLabel = new JLabel("");
        currentGameInfoLabel = new JLabel("");
        selectedValueInfoLabel = new JLabel(selectedNum == 0 ?
                "Click on a field to delete it's value" : "Click on a field to set it's value to " + selectedNum);
        timeLabel = new JLabel(TimeUtil.toDisplayString(0L));
    }

    private void initMenuBar() {
        menuBar = new JMenuBar();

        gameMenu = new JMenu("Game");
        newGameMenuItem = new JMenuItem("New Game");
        checkForMistakesMenuItem = new JMenuItem("Check for mistakes");
        gameMenu.add(newGameMenuItem);
        gameMenu.add(checkForMistakesMenuItem);

        easyMenuItem = new JMenuItem(Level.EASY.stringValue);
        mediumMenuItem = new JMenuItem(Level.MEDIUM.stringValue);
        hardMenuItem = new JMenuItem(Level.HARD.stringValue);

        levelMenu = new JMenu("Level") {{
            add(easyMenuItem);
            add(mediumMenuItem);
            add(hardMenuItem);
        }};

        menuBar.add(gameMenu);
        menuBar.add(levelMenu);
        setJMenuBar(menuBar);

        newGameMenuItem.addActionListener(e -> listener.onNewGameMenuItemClicked());
        checkForMistakesMenuItem.addActionListener(e -> listener.onCheckForMistakesMenuItemClicked());
        easyMenuItem.addActionListener(e -> listener.onLevelMenuItemClicked(Level.EASY));
        mediumMenuItem.addActionListener(e -> listener.onLevelMenuItemClicked(Level.MEDIUM));
        hardMenuItem.addActionListener(e -> listener.onLevelMenuItemClicked(Level.HARD));


    }

    public void setTextForTimeLabel(Long timeElapsedInSeconds) {
        timeLabel.setText(TimeUtil.toDisplayString(timeElapsedInSeconds));
    }
    public void setTextForCurrentGameLevel(Level level) {
        currentGameInfoLabel.setText("Current game's level: " + level.stringValue);
    }

    public void setTextForNextGameLevel(Level level) {
        nextGameInfoLabel.setText("If you create a new game the level will be: " + level.stringValue);
    }

    private void fillBoard(Field[][] fields) {
        for (int i = 0; i < fields.length; i++) {
            for (int j = 0; j < fields.length; j++) {
                int row = i;
                int col = j;
                cells[i][j].setText(fields[i][j].getValue() == 0 ?
                        "" : fields[i][j].getValue() + "");
                cells[i][j].setForeground(fields[row][col].isEditable() ? Color.BLACK : Color.GRAY);
            }
        }
    }

    private void setTextForNumberInfoLabel(int selectedNum) {
        selectedValueInfoLabel.setText(selectedNum == 0 ?
                "Click on a field to delete it's value" : "Click on a field to set it's value to " + selectedNum);
    }

    private void onNumberClicked(int value) {
        selectedNum = value;
        setTextForNumberInfoLabel(selectedNum);
    }

    private void onFieldClickedAtLocation(int row, int col) {
        listener.onFieldClickWithValue(row, col, selectedNum);
    }


    @Override
    public void windowOpened(WindowEvent e) {

    }

    @Override
    public void windowClosing(WindowEvent e) {
        timer.stop();
        listener.onWindowClosing();
    }

    @Override
    public void windowClosed(WindowEvent e) {

    }

    @Override
    public void windowIconified(WindowEvent e) {

    }

    @Override
    public void windowDeiconified(WindowEvent e) {

    }

    @Override
    public void windowActivated(WindowEvent e) {

    }

    @Override
    public void windowDeactivated(WindowEvent e) {

    }


    public interface SudokuFrameListener {

        void onNewGameMenuItemClicked();

        void onCheckForMistakesMenuItemClicked();

        void onLevelMenuItemClicked(Level level);

        void onFieldClickWithValue(int row, int col, int value);

        void onSecondPassed();

        void onWindowClosing();
    }

}
