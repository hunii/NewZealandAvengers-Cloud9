package nz.ac.aut.ense701.gui;

import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Font;
import java.awt.GridLayout;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.UIManager;
import static javax.swing.WindowConstants.DISPOSE_ON_CLOSE;
import nz.ac.aut.ense701.gameModel.Game;
import nz.ac.aut.ense701.gameModel.GameEventListener;
import nz.ac.aut.ense701.gameModel.GameState;
import nz.ac.aut.ense701.gameModel.MoveDirection;
import nz.ac.aut.ense701.gameModel.MovementKeyListener;
import nz.ac.aut.ense701.gameModel.PanelImageHandle;

/*
 * User interface form for Kiwi Avengers.
 * 
 * @author AS
 * @version July 2011
 */

public class KiwiCountUI  extends javax.swing.JFrame implements GameEventListener
{

    /**
     * Creates a GUI for the KiwiIsland game.
     * @param game the game object to represent with this GUI.
     */
    public KiwiCountUI(Game game) 
    {
        assert game != null : "Make sure game object is created before UI";
        this.game = game;
        setAsGameListener();
        panelImage = new PanelImageHandle();
        initComponents();
        initIslandGrid();
        MovementKeyListener moveKeyListener = new MovementKeyListener(game);
        applyKeyListenerToChild(this, moveKeyListener);
        update();
    }
    
    /**
     * This method is called by the game model every time something changes.
     * Trigger an update.
     */
    @Override
    public void gameStateChanged()
    {
        update();
        updateMsg();
        // check for "game over" or "game won"
        if ( game.getState() == GameState.LOST )
        {
            JOptionPane.showMessageDialog(
                    this, 
                    game.getLoseMessage(), "Game over!",
                    JOptionPane.INFORMATION_MESSAGE);
            game.createNewGame();
        }
        else if ( game.getState() == GameState.WON )
        {
            JOptionPane.showMessageDialog(
                    this, 
                    game.getWinMessage(), "Well Done!",
                    JOptionPane.INFORMATION_MESSAGE);
            game.createNewGame();
        }
        else if (game.messageForPlayer())
        {
            JOptionPane.showMessageDialog(
                    this, 
                    game.getPlayerMessage(), "Important Information",
                    JOptionPane.INFORMATION_MESSAGE);   
        }
    }
    
     private void setAsGameListener()
    {
       game.addGameEventListener(this); 
    }
     
    /**
     * Updates the state of the UI based on the state of the game.
     */
    private void update()
    {
        // update the grid square panels
        Component[] components = pnlIsland.getComponents();
        for ( Component c : components )
        {
            // all components in the panel are GridSquarePanels,
            // so we can safely cast
            GridSquarePanel gsp = (GridSquarePanel) c;
            gsp.update();
        }
        
        // update player information
        int[] playerValues = game.getPlayerValues();
        if("NewPlayer".equals(game.getPlayerName())){
            InputPlayerName();
        }
        txtPlayerName.setText(game.getPlayerName());
        progPlayerStamina.setMaximum(playerValues[Game.MAXSTAMINA_INDEX]);
        progPlayerStamina.setValue(playerValues[Game.STAMINA_INDEX]);
        if(playerValues[Game.STAMINA_INDEX] > 20){
            progPlayerStamina.setForeground(new Color(0,102,255));
        }else{
            progPlayerStamina.setForeground(new Color(255,51,51));
        }
        progPlayerHealth.setMaximum(playerValues[Game.MAXHEALTH_INDEX]);
        progPlayerHealth.setValue(playerValues[Game.HEALTH_INDEX]);
        if(playerValues[Game.HEALTH_INDEX] > 20){
            progPlayerHealth.setForeground(new Color(0,102,255));
        }else{
            progPlayerHealth.setForeground(new Color(255,51,51));
        }
        progBackpackWeight.setMaximum(playerValues[Game.MAXWEIGHT_INDEX]);
        progBackpackWeight.setValue(playerValues[Game.WEIGHT_INDEX]);
        if(playerValues[Game.WEIGHT_INDEX] < 8){
            progBackpackWeight.setForeground(new Color(0,102,255));
        }else{
            progBackpackWeight.setForeground(new Color(255,51,51));
        }
        
        //Update City and Predator information
        txtCitiesCounted.setText(Integer.toString(game.getCitiesRemaining())+"/"+game.getTotalCityCount() );
        //txtPredatorsLeft.setText(Integer.toString(game.getPredatorsRemaining()));
        
        // update inventory list
        listInventory.setListData(game.getPlayerInventory());
        listInventory.clearSelection();
        listInventory.setToolTipText(null);
        btnUse.setEnabled(false);
        btnDrop.setEnabled(false);
        
        // update list of visible objects
        listObjects.setListData(game.getOccupantsPlayerPosition());
        listObjects.clearSelection();
        listObjects.setToolTipText(null);
        btnCollect.setEnabled(false);
        
        // update movement buttons
        btnMoveNorth.setEnabled(game.isPlayerMovePossible(MoveDirection.NORTH));
        btnMoveEast.setEnabled( game.isPlayerMovePossible(MoveDirection.EAST));
        btnMoveSouth.setEnabled(game.isPlayerMovePossible(MoveDirection.SOUTH));
        btnMoveWest.setEnabled( game.isPlayerMovePossible(MoveDirection.WEST));
        
    }
    
    private void applyKeyListenerToChild(Container parent, MovementKeyListener mKeyListener){
        Component[]children = parent.getComponents();
        for(Component child : children){
            child.addKeyListener(mKeyListener);
            if(child instanceof Container){
                applyKeyListenerToChild((Container)child, mKeyListener);
            }
        }
    }
    
    private String InputPlayerName(){
        
        String pName = "";
        
        while("".equals(pName)) {
            pName = JOptionPane.showInputDialog("Create your character's name.");
            if(pName == null){
                game.exitGame(0);
            }else if(pName.length() > 10){
                JOptionPane.showMessageDialog(this, "Please re-insert within 10 characters.", "nameLengthAlert", JOptionPane.INFORMATION_MESSAGE);
                pName = "";
            }else{
                boolean isValid  = game.validatePlayerName(pName);
                if(!isValid){
                    JOptionPane.showMessageDialog(this, "Please re-insert Alphabet or Numbers Only.", "nameAlphbetAlert", JOptionPane.INFORMATION_MESSAGE);
                    pName = "";
                }
            }
            pName = pName.replaceAll("\\s","");
        }
        
        game.setPlayerName(pName);
        
        // Displays player's name
        txtPlayerName.setText(game.getPlayerName());
        
        return game.getPlayerName();
    }
    
    private void updateMsg(){
        //messageupdate
        msgLabel.setText(game.msg.getMsg());
    }
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        javax.swing.JPanel pnlContent = new javax.swing.JPanel();
        pnlIsland = new javax.swing.JPanel();
        javax.swing.JPanel pnlControls = new javax.swing.JPanel();
        javax.swing.JPanel pnlPlayer = new javax.swing.JPanel();
        javax.swing.JPanel pnlPlayerData = new javax.swing.JPanel();
        javax.swing.JLabel lblPlayerName = new javax.swing.JLabel();
        txtPlayerName = new javax.swing.JLabel();
        javax.swing.JLabel lblPlayerStamina = new javax.swing.JLabel();
        progPlayerStamina = new javax.swing.JProgressBar();
        javax.swing.JLabel lblPlayerHealth = new javax.swing.JLabel();
        progPlayerHealth = new javax.swing.JProgressBar();
        javax.swing.JLabel lblBackpackWeight = new javax.swing.JLabel();
        progBackpackWeight = new javax.swing.JProgressBar();
        lblCitiesCounted = new javax.swing.JLabel();
        txtCitiesCounted = new javax.swing.JLabel();
        javax.swing.JPanel pnlMovement = new javax.swing.JPanel();
        btnMoveNorth = new javax.swing.JButton();
        btnMoveSouth = new javax.swing.JButton();
        btnMoveEast = new javax.swing.JButton();
        btnMoveWest = new javax.swing.JButton();
        javax.swing.JPanel pnlInventory = new javax.swing.JPanel();
        javax.swing.JScrollPane scrlInventory = new javax.swing.JScrollPane();
        listInventory = new javax.swing.JList();
        btnDrop = new javax.swing.JButton();
        btnUse = new javax.swing.JButton();
        javax.swing.JPanel pnlObjects = new javax.swing.JPanel();
        javax.swing.JScrollPane scrlObjects = new javax.swing.JScrollPane();
        listObjects = new javax.swing.JList();
        btnCollect = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        introButton = new javax.swing.JButton();
        instructionButton = new javax.swing.JButton();
        developerButton = new javax.swing.JButton();
        newGameButton = new javax.swing.JButton();
        exitButton = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        msgLabel = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Kiwi Avengers");
        setResizable(false);

        pnlContent.setBackground(new java.awt.Color(51, 51, 51));
        pnlContent.setBorder(javax.swing.BorderFactory.createEmptyBorder(10, 10, 10, 10));
        pnlContent.setLayout(new java.awt.BorderLayout(10, 0));

        javax.swing.GroupLayout pnlIslandLayout = new javax.swing.GroupLayout(pnlIsland);
        pnlIsland.setLayout(pnlIslandLayout);
        pnlIslandLayout.setHorizontalGroup(
            pnlIslandLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        pnlIslandLayout.setVerticalGroup(
            pnlIslandLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 567, Short.MAX_VALUE)
        );

        pnlContent.add(pnlIsland, java.awt.BorderLayout.CENTER);

        pnlControls.setLayout(new java.awt.GridBagLayout());

        pnlPlayer.setBackground(new java.awt.Color(51, 51, 51));
        pnlPlayer.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Player", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Times New Roman", 1, 12), new java.awt.Color(255, 255, 255))); // NOI18N
        pnlPlayer.setLayout(new java.awt.BorderLayout());

        pnlPlayerData.setBackground(new java.awt.Color(51, 51, 51));
        pnlPlayerData.setBorder(javax.swing.BorderFactory.createEmptyBorder(5, 5, 5, 5));
        pnlPlayerData.setLayout(new java.awt.GridBagLayout());

        lblPlayerName.setBackground(new java.awt.Color(0, 153, 153));
        lblPlayerName.setForeground(new java.awt.Color(255, 255, 255));
        lblPlayerName.setText("Name:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.weighty = 1.0;
        pnlPlayerData.add(lblPlayerName, gridBagConstraints);

        txtPlayerName.setBackground(new java.awt.Color(0, 153, 153));
        txtPlayerName.setForeground(new java.awt.Color(255, 255, 255));
        txtPlayerName.setText("Player Name");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(0, 10, 0, 0);
        pnlPlayerData.add(txtPlayerName, gridBagConstraints);

        lblPlayerStamina.setBackground(new java.awt.Color(0, 204, 204));
        lblPlayerStamina.setForeground(new java.awt.Color(255, 255, 255));
        lblPlayerStamina.setText("Stamina:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(10, 0, 0, 0);
        pnlPlayerData.add(lblPlayerStamina, gridBagConstraints);

        progPlayerStamina.setForeground(new java.awt.Color(0, 102, 255));
        progPlayerStamina.setStringPainted(true);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(10, 10, 0, 0);
        pnlPlayerData.add(progPlayerStamina, gridBagConstraints);

        lblPlayerHealth.setForeground(new java.awt.Color(255, 255, 255));
        lblPlayerHealth.setText("Health:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(10, 0, 0, 0);
        pnlPlayerData.add(lblPlayerHealth, gridBagConstraints);

        progPlayerHealth.setForeground(new java.awt.Color(0, 102, 255));
        progPlayerHealth.setStringPainted(true);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(10, 10, 0, 0);
        pnlPlayerData.add(progPlayerHealth, gridBagConstraints);

        lblBackpackWeight.setForeground(new java.awt.Color(255, 255, 255));
        lblBackpackWeight.setText("Backpack Weight:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(10, 0, 0, 0);
        pnlPlayerData.add(lblBackpackWeight, gridBagConstraints);

        progBackpackWeight.setForeground(new java.awt.Color(0, 102, 255));
        progBackpackWeight.setStringPainted(true);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(10, 10, 0, 0);
        pnlPlayerData.add(progBackpackWeight, gridBagConstraints);

        lblCitiesCounted.setForeground(new java.awt.Color(255, 255, 255));
        lblCitiesCounted.setText("Saved Cities :");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        pnlPlayerData.add(lblCitiesCounted, gridBagConstraints);

        txtCitiesCounted.setForeground(new java.awt.Color(255, 255, 255));
        txtCitiesCounted.setText("0");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        pnlPlayerData.add(txtCitiesCounted, gridBagConstraints);

        pnlPlayer.add(pnlPlayerData, java.awt.BorderLayout.WEST);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 0.5;
        pnlControls.add(pnlPlayer, gridBagConstraints);

        pnlMovement.setBackground(new java.awt.Color(51, 51, 51));
        pnlMovement.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Movement", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Times New Roman", 1, 12), new java.awt.Color(255, 255, 255))); // NOI18N
        pnlMovement.setLayout(new java.awt.GridBagLayout());

        btnMoveNorth.setBackground(new java.awt.Color(51, 51, 51));
        btnMoveNorth.setForeground(new java.awt.Color(100, 100, 200));
        btnMoveNorth.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/up.png"))); // NOI18N
        btnMoveNorth.setFocusable(false);
        btnMoveNorth.setMaximumSize(new java.awt.Dimension(39, 23));
        btnMoveNorth.setMinimumSize(new java.awt.Dimension(39, 23));
        btnMoveNorth.setPreferredSize(new java.awt.Dimension(39, 23));
        btnMoveNorth.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnMoveNorthActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        pnlMovement.add(btnMoveNorth, gridBagConstraints);

        btnMoveSouth.setBackground(new java.awt.Color(51, 51, 51));
        btnMoveSouth.setForeground(new java.awt.Color(100, 100, 200));
        btnMoveSouth.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/down.png"))); // NOI18N
        btnMoveSouth.setFocusable(false);
        btnMoveSouth.setMaximumSize(new java.awt.Dimension(39, 23));
        btnMoveSouth.setMinimumSize(new java.awt.Dimension(39, 23));
        btnMoveSouth.setPreferredSize(new java.awt.Dimension(39, 23));
        btnMoveSouth.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnMoveSouthActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        pnlMovement.add(btnMoveSouth, gridBagConstraints);

        btnMoveEast.setBackground(new java.awt.Color(51, 51, 51));
        btnMoveEast.setForeground(new java.awt.Color(100, 100, 200));
        btnMoveEast.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/right.png"))); // NOI18N
        btnMoveEast.setFocusable(false);
        btnMoveEast.setMaximumSize(new java.awt.Dimension(39, 23));
        btnMoveEast.setMinimumSize(new java.awt.Dimension(39, 23));
        btnMoveEast.setPreferredSize(new java.awt.Dimension(39, 23));
        btnMoveEast.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnMoveEastActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        pnlMovement.add(btnMoveEast, gridBagConstraints);

        btnMoveWest.setBackground(new java.awt.Color(51, 51, 51));
        btnMoveWest.setForeground(new java.awt.Color(100, 100, 200));
        btnMoveWest.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/left.png"))); // NOI18N
        btnMoveWest.setFocusable(false);
        btnMoveWest.setMaximumSize(new java.awt.Dimension(39, 23));
        btnMoveWest.setMinimumSize(new java.awt.Dimension(39, 23));
        btnMoveWest.setPreferredSize(new java.awt.Dimension(39, 23));
        btnMoveWest.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnMoveWestActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        pnlMovement.add(btnMoveWest, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 0.5;
        pnlControls.add(pnlMovement, gridBagConstraints);

        pnlInventory.setBackground(new java.awt.Color(51, 51, 51));
        pnlInventory.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Inventory", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Times New Roman", 1, 12), new java.awt.Color(255, 255, 255))); // NOI18N
        pnlInventory.setLayout(new java.awt.GridBagLayout());

        listInventory.setModel(new javax.swing.AbstractListModel() {
            String[] strings = { "Item 1", "Item 2", "Item 3" };
            public int getSize() { return strings.length; }
            public Object getElementAt(int i) { return strings[i]; }
        });
        listInventory.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        listInventory.setVisibleRowCount(3);
        listInventory.addListSelectionListener(new javax.swing.event.ListSelectionListener() {
            public void valueChanged(javax.swing.event.ListSelectionEvent evt) {
                listInventoryValueChanged(evt);
            }
        });
        scrlInventory.setViewportView(listInventory);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        pnlInventory.add(scrlInventory, gridBagConstraints);

        btnDrop.setBackground(new java.awt.Color(51, 51, 51));
        btnDrop.setForeground(new java.awt.Color(51, 51, 51));
        btnDrop.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/drop_btn.png"))); // NOI18N
        btnDrop.setMaximumSize(new java.awt.Dimension(45, 23));
        btnDrop.setMinimumSize(new java.awt.Dimension(45, 23));
        btnDrop.setPreferredSize(new java.awt.Dimension(45, 23));
        btnDrop.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDropActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        pnlInventory.add(btnDrop, gridBagConstraints);

        btnUse.setBackground(new java.awt.Color(51, 51, 51));
        btnUse.setForeground(new java.awt.Color(51, 51, 51));
        btnUse.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/use_btn.png"))); // NOI18N
        btnUse.setMaximumSize(new java.awt.Dimension(45, 23));
        btnUse.setMinimumSize(new java.awt.Dimension(45, 23));
        btnUse.setPreferredSize(new java.awt.Dimension(45, 23));
        btnUse.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUseActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        pnlInventory.add(btnUse, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        pnlControls.add(pnlInventory, gridBagConstraints);

        pnlObjects.setBackground(new java.awt.Color(51, 51, 51));
        pnlObjects.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Items on ground", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Times New Roman", 1, 12), new java.awt.Color(255, 255, 255))); // NOI18N
        java.awt.GridBagLayout pnlObjectsLayout = new java.awt.GridBagLayout();
        pnlObjectsLayout.columnWidths = new int[] {0, 5, 0};
        pnlObjectsLayout.rowHeights = new int[] {0, 5, 0};
        pnlObjects.setLayout(pnlObjectsLayout);

        listObjects.setModel(new javax.swing.AbstractListModel() {
            String[] strings = { "Item 1", "Item 2", "Item 3" };
            public int getSize() { return strings.length; }
            public Object getElementAt(int i) { return strings[i]; }
        });
        listObjects.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        listObjects.setVisibleRowCount(3);
        listObjects.addListSelectionListener(new javax.swing.event.ListSelectionListener() {
            public void valueChanged(javax.swing.event.ListSelectionEvent evt) {
                listObjectsValueChanged(evt);
            }
        });
        scrlObjects.setViewportView(listObjects);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        pnlObjects.add(scrlObjects, gridBagConstraints);

        btnCollect.setBackground(new java.awt.Color(51, 51, 51));
        btnCollect.setForeground(new java.awt.Color(51, 51, 51));
        btnCollect.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/pickUp_btn2.png"))); // NOI18N
        btnCollect.setToolTipText("");
        btnCollect.setMaximumSize(new java.awt.Dimension(61, 23));
        btnCollect.setMinimumSize(new java.awt.Dimension(61, 23));
        btnCollect.setPreferredSize(new java.awt.Dimension(61, 23));
        btnCollect.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCollectActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.SOUTHWEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        pnlObjects.add(btnCollect, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        pnlControls.add(pnlObjects, gridBagConstraints);

        pnlContent.add(pnlControls, java.awt.BorderLayout.EAST);

        jPanel1.setBackground(new java.awt.Color(51, 51, 51));
        jPanel1.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLabel1.setBackground(new java.awt.Color(51, 51, 51));
        jLabel1.setFont(new java.awt.Font("Californian FB", 3, 48)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("Kiwi Avengers");

        introButton.setBackground(new java.awt.Color(51, 51, 51));
        introButton.setForeground(new java.awt.Color(255, 255, 255));
        introButton.setText("Game Story(F2)");
        introButton.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        introButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                introButtonActionPerformed(evt);
            }
        });

        instructionButton.setBackground(new java.awt.Color(51, 51, 51));
        instructionButton.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        instructionButton.setForeground(new java.awt.Color(255, 255, 255));
        instructionButton.setText("How to play(F1)");
        instructionButton.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        instructionButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                instructionButtonActionPerformed(evt);
            }
        });

        developerButton.setBackground(new java.awt.Color(51, 51, 51));
        developerButton.setForeground(new java.awt.Color(255, 255, 255));
        developerButton.setText("Developers(F3)");
        developerButton.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        developerButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                developerButtonActionPerformed(evt);
            }
        });

        newGameButton.setBackground(new java.awt.Color(51, 51, 51));
        newGameButton.setForeground(new java.awt.Color(255, 255, 255));
        newGameButton.setText("New Game(F11)");
        newGameButton.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        newGameButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                newGameButtonActionPerformed(evt);
            }
        });

        exitButton.setBackground(new java.awt.Color(51, 51, 51));
        exitButton.setForeground(new java.awt.Color(255, 255, 255));
        exitButton.setText("End Game(F12)");
        exitButton.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        exitButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                exitButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 295, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(instructionButton)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(introButton)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(developerButton)
                .addGap(11, 11, 11)
                .addComponent(newGameButton)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(exitButton)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(introButton, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(instructionButton, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(developerButton, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(newGameButton, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(exitButton, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pnlContent.add(jPanel1, java.awt.BorderLayout.PAGE_START);

        jPanel2.setBackground(new java.awt.Color(204, 204, 204));
        jPanel2.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLabel2.setFont(new java.awt.Font("Calibri", 1, 18)); // NOI18N
        jLabel2.setText("Player's Action:");

        msgLabel.setFont(new java.awt.Font("Calibri", 0, 18)); // NOI18N

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(msgLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 726, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(76, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(msgLabel))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        msgLabel.getAccessibleContext().setAccessibleName("messageLabel");

        pnlContent.add(jPanel2, java.awt.BorderLayout.PAGE_END);

        getContentPane().add(pnlContent, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnMoveEastActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnMoveEastActionPerformed
        game.playerMove(MoveDirection.EAST);
    }//GEN-LAST:event_btnMoveEastActionPerformed

    private void btnMoveNorthActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnMoveNorthActionPerformed
        game.playerMove(MoveDirection.NORTH);
    }//GEN-LAST:event_btnMoveNorthActionPerformed

    private void btnMoveSouthActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnMoveSouthActionPerformed
        game.playerMove(MoveDirection.SOUTH);
    }//GEN-LAST:event_btnMoveSouthActionPerformed

    private void btnMoveWestActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnMoveWestActionPerformed
        game.playerMove(MoveDirection.WEST);
    }//GEN-LAST:event_btnMoveWestActionPerformed

    private void btnCollectActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCollectActionPerformed
        Object obj = listObjects.getSelectedValue();
        game.collectItem(obj);
    }//GEN-LAST:event_btnCollectActionPerformed

    private void btnDropActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDropActionPerformed
        game.dropItem(listInventory.getSelectedValue());
    }//GEN-LAST:event_btnDropActionPerformed

    private void listObjectsValueChanged(javax.swing.event.ListSelectionEvent evt) {//GEN-FIRST:event_listObjectsValueChanged
        Object occ = listObjects.getSelectedValue();
        if ( occ != null )
        {
            btnCollect.setEnabled(game.canCollect(occ));
            listObjects.setToolTipText(game.getOccupantDescription(occ));
        }
    }//GEN-LAST:event_listObjectsValueChanged

    private void btnUseActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUseActionPerformed
        game.useItem( listInventory.getSelectedValue());
    }//GEN-LAST:event_btnUseActionPerformed

    private void listInventoryValueChanged(javax.swing.event.ListSelectionEvent evt) {//GEN-FIRST:event_listInventoryValueChanged
        Object item =  listInventory.getSelectedValue();
        btnDrop.setEnabled(true);
        if ( item != null )
        {
            btnUse.setEnabled(game.canUse(item));
            listInventory.setToolTipText(game.getOccupantDescription(item));
        }
    }//GEN-LAST:event_listInventoryValueChanged

    private void exitButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_exitButtonActionPerformed
        int exitBut = JOptionPane.showConfirmDialog(this, "Do you want to exit the game?", "Exit?",JOptionPane.OK_CANCEL_OPTION);
        game.exitGame(exitBut);
    }//GEN-LAST:event_exitButtonActionPerformed

    private void instructionButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_instructionButtonActionPerformed
        final GameInstruct gInstruct  = new GameInstruct();
        
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() 
            {
                gInstruct.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
                gInstruct.setVisible(true);
            }
        });
    }//GEN-LAST:event_instructionButtonActionPerformed

    private void introButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_introButtonActionPerformed
        final GameStory gameStory  = new GameStory();
        
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() 
            {
                gameStory.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
                gameStory.setVisible(true);
            }
        });
    }//GEN-LAST:event_introButtonActionPerformed

    private void newGameButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_newGameButtonActionPerformed
        int restartBut = JOptionPane.showConfirmDialog(this, "Do you want to Restart the game?", "Restart?",JOptionPane.OK_CANCEL_OPTION);
        game.restartGame(restartBut);
    }//GEN-LAST:event_newGameButtonActionPerformed

    private void developerButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_developerButtonActionPerformed
        String developer = game.getDevelopers();
        JOptionPane.showMessageDialog(this,developer,"Version & Developers",JOptionPane.INFORMATION_MESSAGE);
    }//GEN-LAST:event_developerButtonActionPerformed
    
    /**
     * Creates and initialises the island grid.
     */
    private void initIslandGrid()
    {
        // Add the grid
        int rows    = game.getNumRows();
        int columns = game.getNumColumns();
        // set up the layout manager for the island grid panel
        pnlIsland.setLayout(new GridLayout(rows, columns));
        // create all the grid square panels and add them to the panel
        // the layout manager of the panel takes care of assigning them to the
        // the right position
        for ( int row = 0 ; row < rows ; row++ )
        {
            for ( int col = 0 ; col < columns ; col++ )
            {
                pnlIsland.add(new GridSquarePanel(game,panelImage, row, col));
            }
        }
    }
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCollect;
    private javax.swing.JButton btnDrop;
    private javax.swing.JButton btnMoveEast;
    private javax.swing.JButton btnMoveNorth;
    private javax.swing.JButton btnMoveSouth;
    private javax.swing.JButton btnMoveWest;
    private javax.swing.JButton btnUse;
    private javax.swing.JButton developerButton;
    private javax.swing.JButton exitButton;
    private javax.swing.JButton instructionButton;
    private javax.swing.JButton introButton;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JLabel lblCitiesCounted;
    private javax.swing.JList listInventory;
    private javax.swing.JList listObjects;
    private javax.swing.JLabel msgLabel;
    private javax.swing.JButton newGameButton;
    private javax.swing.JPanel pnlIsland;
    private javax.swing.JProgressBar progBackpackWeight;
    private javax.swing.JProgressBar progPlayerHealth;
    private javax.swing.JProgressBar progPlayerStamina;
    private javax.swing.JLabel txtCitiesCounted;
    private javax.swing.JLabel txtPlayerName;
    // End of variables declaration//GEN-END:variables

    private Game game;
    private PanelImageHandle panelImage;
}
