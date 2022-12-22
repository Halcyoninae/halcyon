package com.jackmeng.core.gui;

import com.jackmeng.core.abst.evnt_AddPlaylist;
import com.jackmeng.core.util.pstream;

import javax.swing.*;

import static com.jackmeng.const_Lang.*;

import java.awt.*;
import java.io.Serializable;

public final class gui_HalcyonPlaylistSelect
    implements
    Runnable,
    Serializable
{
  private JFileChooser chooser;
  /*----------------------- /
  / private boolean opened; /
  /------------------------*/
  private final JFrame parent;
  /*----------------------------- /
  / private final TitledFrame tf; /
  /------------------------------*/
  private transient evnt_AddPlaylist playlist;

  public gui_HalcyonPlaylistSelect(JFrame parent, String currDir)
  {
    /*--------------------------------------------- /
    / chooser.addFocusListener(new FocusAdapter() { /
    /   @Override                                   /
    /   public void focusLost(FocusEvent e) {       /
    /     tf.expose().requestFocus();               /
    /   }                                           /
    / });                                           /
    /----------------------------------------------*/
    /*----------------------------------------------------------------------------------------------- /
    / tf = new TitledFrame(new TitleBarConfig(_lang(LANG_PLAYLIST_SELECT_DIALOG_TITLE_SUB),           /
    /     use_ResourceFetcher.fetcher.getFromAsImageIcon(const_ResourceManager.GUI_PROGRAM_LOGO),     /
    /     use_HalcyonProperties.regularFont().deriveFont(13F), const_ColorManager.DEFAULT_GREEN_FG,   /
    /     const_ColorManager.DEFAULT_BG, const_ColorManager.DEFAULT_RED_FG,                           /
    /     null, null, null, const_ColorManager.DEFAULT_DARK_BG), 20, chooser, () -> setTruth(false)); /
    /------------------------------------------------------------------------------------------------*/
    /*-------------------------------------- /
    / tf.expose().setAutoRequestFocus(true); /
    / tf.expose().setFocusable(true);        /
    /---------------------------------------*/

    chooser = new JFileChooser(currDir == null ? "." : currDir);
    chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
    chooser.setAcceptAllFileFilterUsed(false);
    chooser.setApproveButtonText(_lang(LANG_PLAYLIST_SELECT_APPROVE_BUTTON));
    chooser.setApproveButtonToolTipText(_lang(LANG_APPS_ADD_PLAYLIST_TOOLTIP));
    chooser.setDialogTitle("Halcyon ~ " + _lang(LANG_PLAYLIST_SELECT_APPROVE_BUTTON));
    chooser.setFileHidingEnabled(false);
    chooser.setMultiSelectionEnabled(false);
    chooser.setPreferredSize(new Dimension(550, 580));
    this.parent = parent;
  }

  public void setListener(evnt_AddPlaylist l)
  {
    this.playlist = l;
  }

  /*--------------------------------- /
  / public void setTruth(boolean t) { /
  /   this.opened = t;                /
  /   tf.expose().dispose();          /
  / }                                 /
  /----------------------------------*/

  @Override
  public void run()
  {
    int result = chooser.showOpenDialog(parent);
    if (result == JFileChooser.APPROVE_OPTION && playlist != null)
    {
      pstream.log.info(result + " " + chooser.getSelectedFile());
      playlist.addPlaylist(chooser.getSelectedFile().getAbsolutePath());
    }
  }
}
