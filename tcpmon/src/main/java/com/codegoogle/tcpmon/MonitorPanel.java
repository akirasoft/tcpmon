/*
 * Copyright (c) 2004-2011 tcpmon authors. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * $Id$
 */
package com.codegoogle.tcpmon;

import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.AbstractTableModel;

/**
 * GUI panel for monitor window
 *
 * @author Inderjeet Singh
 */
public class MonitorPanel extends javax.swing.JPanel implements CallBack, ListSelectionListener {

  private static final long serialVersionUID = 1L;

  /**
   * Creates new form MonitorPanel
   */
  public MonitorPanel() {
    initComponents();
  }

  /**
   * This method is called from within the constructor to
   * initialize the form.
   * WARNING: Do NOT modify this code. The content of this method is
   * always regenerated by the Form Editor.
   */
  private void initComponents() {//GEN-BEGIN:initComponents
    topPanel = new javax.swing.JPanel();
    bStop = new javax.swing.JButton();
    lLocalPort = new javax.swing.JLabel();
    tfLocalPort = new javax.swing.JTextField();
    lRemoteHost = new javax.swing.JLabel();
    tfRemoteHost = new javax.swing.JTextField();
    lRemotePort = new javax.swing.JLabel();
    tfRemotePort = new javax.swing.JTextField();
    bCloseTab = new javax.swing.JButton();
    centerPanel = new javax.swing.JPanel();
    tablePanel = new javax.swing.JPanel();
    spRequestsTable = new javax.swing.JScrollPane();
    tRequests = new javax.swing.JTable();
    tRequests.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    tRequests.getSelectionModel().addListSelectionListener(this);
    deleteButtonPanel = new javax.swing.JPanel();
    bDeleteRow = new javax.swing.JButton();
    bDeleteAllRows = new javax.swing.JButton();
    bSubmitToServer = new javax.swing.JButton();
    splitPane = new javax.swing.JSplitPane();
    spForward = new javax.swing.JScrollPane();
    tpForward = new javax.swing.JTextPane();
    spReverse = new javax.swing.JScrollPane();
    tpReverse = new javax.swing.JTextPane();

    setLayout(new java.awt.BorderLayout(5, 5));

    bStop.setText("Stop Monitor");
    bStop.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        bStopActionPerformed(evt);
      }
    });

    topPanel.add(bStop);

    lLocalPort.setText("Local Port: ");
    topPanel.add(lLocalPort);

    tfLocalPort.setText("8080");
    tfLocalPort.setEnabled(false);
    topPanel.add(tfLocalPort);

    lRemoteHost.setText("Server Name: ");
    topPanel.add(lRemoteHost);

    tfRemoteHost.setText("127.0.0.1");
    tfRemoteHost.setEnabled(false);
    topPanel.add(tfRemoteHost);

    lRemotePort.setText("Server Port: ");
    topPanel.add(lRemotePort);

    tfRemotePort.setText("8080");
    tfRemotePort.setEnabled(false);
    topPanel.add(tfRemotePort);

    bCloseTab.setText("Close Tab");
    bCloseTab.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        bCloseTabActionPerformed(evt);
      }
    });

    topPanel.add(bCloseTab);

    add(topPanel, java.awt.BorderLayout.NORTH);

    centerPanel.setLayout(new java.awt.BorderLayout());

    tablePanel.setLayout(new java.awt.BorderLayout());

    spRequestsTable.setPreferredSize(new java.awt.Dimension(452, 102));
    tRequests.setBorder(new javax.swing.border.BevelBorder(javax.swing.border.BevelBorder.RAISED));
    tRequests.setModel(getTableModel());
    spRequestsTable.setViewportView(tRequests);

    tablePanel.add(spRequestsTable, java.awt.BorderLayout.CENTER);

    deleteButtonPanel.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT));

    bDeleteRow.setText("Delete Row");
    bDeleteRow.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        bDeleteRowActionPerformed(evt);
      }
    });

    deleteButtonPanel.add(bDeleteRow);

    bDeleteAllRows.setText("Delete All Rows");
    bDeleteAllRows.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        bDeleteAllRowsActionPerformed(evt);
      }
    });

    deleteButtonPanel.add(bDeleteAllRows);

    bSubmitToServer.setText("Submit to Server");
    bSubmitToServer.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        bSubmitToServerActionPerformed(evt);
      }
    });

    deleteButtonPanel.add(bSubmitToServer);

    tablePanel.add(deleteButtonPanel, java.awt.BorderLayout.SOUTH);

    centerPanel.add(tablePanel, java.awt.BorderLayout.NORTH);

    splitPane.setOrientation(javax.swing.JSplitPane.VERTICAL_SPLIT);
    splitPane.setResizeWeight(0.5);
    splitPane.setLastDividerLocation(-5);
    splitPane.setOneTouchExpandable(true);
    spForward.setViewportView(tpForward);

    splitPane.setTopComponent(spForward);

    spReverse.setViewportView(tpReverse);

    splitPane.setBottomComponent(spReverse);

    centerPanel.add(splitPane, java.awt.BorderLayout.CENTER);

    add(centerPanel, java.awt.BorderLayout.CENTER);

  }//GEN-END:initComponents

  private void bCloseTabActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bCloseTabActionPerformed
    // Close this tab and send
    bCloseTab.setEnabled(false);
    tableModel.deleteAllRows();
    tunnel.stopNow();
    getParent().remove(this);
  }//GEN-LAST:event_bCloseTabActionPerformed

  private void bSubmitToServerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bSubmitToServerActionPerformed
    String requestData = tpForward.getText();
    RequestSender sender = new RequestSender(requestData, tc, this);
  }//GEN-LAST:event_bSubmitToServerActionPerformed

  private void bDeleteAllRowsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bDeleteAllRowsActionPerformed
    tableModel.deleteAllRows();
    // And clear out captured data as well:
    tpForward.setText("");
    tpReverse.setText("");
  }//GEN-LAST:event_bDeleteAllRowsActionPerformed

  private void bDeleteRowActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bDeleteRowActionPerformed
    int rownum = tRequests.getSelectedRow();
    if (rownum >= 0) {
      tableModel.deleteRow(rownum);
    }
  }//GEN-LAST:event_bDeleteRowActionPerformed

  private void bStopActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bStopActionPerformed
    tunnel.stopNow();
    bStop.setEnabled(false);
  }//GEN-LAST:event_bStopActionPerformed

  private RequestsTableModel tableModel = new RequestsTableModel();

  public AbstractTableModel getTableModel() {
    return tableModel;
  }

  public String getForwardData() {
    int selectedRow = tRequests.getSelectedRow();
    return tableModel.getForwardData(selectedRow);
  }

  public void valueChanged(ListSelectionEvent e) {
    //Ignore extra messages.
    if (e.getValueIsAdjusting())
      return;

    ListSelectionModel lsm = (ListSelectionModel) e.getSource();
    if (lsm.isSelectionEmpty()) {
      // no rows are selected
    } else {
      int selectedRow = lsm.getMinSelectionIndex();
      tpForward.setText(tableModel.getForwardData(selectedRow));
      tpReverse.setText(tableModel.getReverseData(selectedRow));
    }
  }

  public void connectionFinished(CallBack.CallBackData data) {
    String fdata = data.getForwardData();
    tpForward.setText(fdata);
    tpReverse.setText(data.getReverseData());
    int len = fdata.length();
    if (len > 10) {
      len = 10;
    }
    String requestHead = fdata.substring(0, len) + "...";
    tableModel.addEntry(data.getState(), data.getTime(), data.getSourceHost(),
        tc.serverName, requestHead, fdata, data.getReverseData());
  }

  void start(TunnelConfig tc) {
    try {
      this.tc = tc;
      tfLocalPort.setText(String.valueOf(tc.localPort));
      tfRemotePort.setText(String.valueOf(tc.serverPort));
      tfRemoteHost.setText(tc.serverName);
      tunnel = new Tunnel(tc, this);
    } catch (Exception e) {
      String stackTrace = Utils.extractStackTrace(e);
      tpForward.setText(stackTrace);
    }
  }

  TunnelConfig tc;
  Tunnel tunnel;

  // Variables declaration - do not modify//GEN-BEGIN:variables
  private javax.swing.JButton bCloseTab;
  private javax.swing.JButton bDeleteAllRows;
  private javax.swing.JButton bDeleteRow;
  private javax.swing.JButton bStop;
  private javax.swing.JButton bSubmitToServer;
  private javax.swing.JPanel centerPanel;
  private javax.swing.JPanel deleteButtonPanel;
  private javax.swing.JLabel lLocalPort;
  private javax.swing.JLabel lRemoteHost;
  private javax.swing.JLabel lRemotePort;
  private javax.swing.JScrollPane spForward;
  private javax.swing.JScrollPane spRequestsTable;
  private javax.swing.JScrollPane spReverse;
  private javax.swing.JSplitPane splitPane;
  private javax.swing.JTable tRequests;
  private javax.swing.JPanel tablePanel;
  private javax.swing.JTextField tfLocalPort;
  private javax.swing.JTextField tfRemoteHost;
  private javax.swing.JTextField tfRemotePort;
  private javax.swing.JPanel topPanel;
  private javax.swing.JTextPane tpForward;
  private javax.swing.JTextPane tpReverse;
  // End of variables declaration//GEN-END:variables
}
