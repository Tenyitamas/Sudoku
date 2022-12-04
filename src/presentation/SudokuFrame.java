package presentation;

import domain.model.Level;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainFrame extends JFrame implements ActionListener {

    // MENU
    static JMenuBar menuBar;

    static JMenu gameMenu;
    static JMenuItem newGameMenuItem;

    static JMenu levelMenu;
    static JMenuItem easyMenuItem;
    static JMenuItem mediumMenuItem;
    static JMenuItem hardMenuItem;

    // BOARD

    // NUMS

    // INFO LABELS
    static JTextField nextGameInfoLabel;

    // PANELS
    JPanel content;
    JPanel boardPanel;
    JPanel infoLabelsPanel;
    JPanel numbersPanel;

    static GridBagLayout sudokuLayout;


    private MainFrameListener listener;


    public MainFrame(MainFrameListener listener) {
        this.listener = listener;

        setTitle("Sudoku");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);


        initMenuBar();
        // InitBoard
        // InitNumbers
        // InitInfoLabels
        initInfoLabels();
        initPanels();


        setSize(1000, 1000);
        setResizable(false);

        getContentPane().setBackground(Color.BLUE);

    }

    private void initPanels() {
        boardPanel = new JPanel(new GridLayout(9,9)) {{
            for (int i = 0; i < 9; i++) {
                for (int j = 0; j < 9; j++) {
                    add(new JButton("{%d, %d}".formatted(i,j)) {{
                        setPreferredSize(new Dimension(61, 61));
                    }}) ;
                }
            }
            setSize(new Dimension(600, 600));
        }};
        numbersPanel = new JPanel(new FlowLayout(FlowLayout.CENTER)) {{
            add(new JButton("ahahdbha"));
            add(new JButton("ahahdbha"));
            add(new JButton("ahahdbha"));
            add(new JButton("ahahdbha"));
            setPreferredSize(new Dimension(1000, 100));
        }};
        infoLabelsPanel = new JPanel(new GridLayout(3, 1)){{
            add(nextGameInfoLabel);
            add(new JTextField("KDAHJD"));
            setPreferredSize(new Dimension(1000, 100));
        }};

        content = new JPanel(new BorderLayout()) {{
            add(infoLabelsPanel, BorderLayout.NORTH);

            add(boardPanel, BorderLayout.CENTER);

            add(numbersPanel, BorderLayout.SOUTH);
        }};

        getContentPane().add(content);

    }

    private void initInfoLabels() {
        nextGameInfoLabel = new JTextField("COMEOEMDONED jkasbdjka bdbasbdhabhdbajsba d a fasj d ha dj");
    }

    private void initMenuBar() {
        menuBar = new JMenuBar();

        gameMenu = new JMenu("Game");
        newGameMenuItem = new JMenuItem("New Game");
        gameMenu.add(newGameMenuItem);

        easyMenuItem = new JMenuItem("Easy");
        mediumMenuItem = new JMenuItem("Medium");
        hardMenuItem = new JMenuItem("Hard");
        levelMenu = new JMenu("Level") {{
            add(easyMenuItem);
            add(mediumMenuItem);
            add(hardMenuItem);
        }};

        menuBar.add(gameMenu);
        menuBar.add(levelMenu);
        setJMenuBar(menuBar);

        newGameMenuItem.addActionListener(this);
        easyMenuItem.addActionListener(this);
        mediumMenuItem.addActionListener(this);
        hardMenuItem.addActionListener(this);

    }

    public void setTextForNextGameLevel(Level level) {
        //nextGameInfoLabel.setText("If you start a new game it will be level: " + level.stringValue);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();
        if (newGameMenuItem.equals(source)) {
            listener.onNewGameMenuItemClicked();
        } else if (easyMenuItem.equals(source)) {
            listener.onLevelMenuItemClicked(Level.EASY);
        } else if(mediumMenuItem.equals(source)) {
            listener.onLevelMenuItemClicked(Level.MEDIUM);
        } else if(hardMenuItem.equals(source)) {
            listener.onLevelMenuItemClicked(Level.HARD);
        }
    }

    public interface MainFrameListener {
        public void onNewGameMenuItemClicked();

        public void onLevelMenuItemClicked(Level level);
    }
}
