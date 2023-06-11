package net.sourceforge.squirrel_sql.client.session.action.dbdiff.tableselectiondiff;

import net.sourceforge.squirrel_sql.client.Main;
import net.sourceforge.squirrel_sql.client.session.action.dbdiff.DBDIffService;
import net.sourceforge.squirrel_sql.fw.gui.ClipboardUtil;
import net.sourceforge.squirrel_sql.fw.gui.action.copyasmarkdown.CopyAsMarkDown;
import net.sourceforge.squirrel_sql.fw.util.StringManager;
import net.sourceforge.squirrel_sql.fw.util.StringManagerFactory;
import net.sourceforge.squirrel_sql.fw.util.StringUtilities;

import javax.swing.*;
import java.nio.file.Path;

public class TableSelectionDiff
{
   private static final StringManager s_stringMgr = StringManagerFactory.getStringManager(TableSelectionDiff.class);

   public static JMenu createMenu(DiffTableProvider diffTableProvider)
   {
      JMenu ret = new JMenu(s_stringMgr.getString("TableSelectionDiff.submenu.title"));

      JMenuItem mnuSelectForCompare = new JMenuItem(s_stringMgr.getString("TableSelectionDiff.select.for.compare"));
      mnuSelectForCompare.addActionListener(e -> onSelectForCompare(diffTableProvider));
      ret.add(mnuSelectForCompare);

      JMenuItem mnuCompare = new JMenuItem(s_stringMgr.getString("TableSelectionDiff.compare"));
      mnuCompare.addActionListener(e -> onCompare(diffTableProvider));
      ret.add(mnuCompare);

      JMenuItem mnuCompareToClip = new JMenuItem(s_stringMgr.getString("TableSelectionDiff.compare.to.clipboard"));
      mnuCompareToClip.addActionListener(e -> onCompareToClip(diffTableProvider));
      ret.add(mnuCompareToClip);

      return ret;
   }

   private static void onSelectForCompare(DiffTableProvider diffTableProvider)
   {
      int nbrSelRows = diffTableProvider.getTable().getSelectedRowCount();
      int nbrSelCols = diffTableProvider.getTable().getSelectedColumnCount();

      if(0 == nbrSelRows || 0 == nbrSelCols)
      {
         Main.getApplication().getMessageHandler().showErrorMessage(s_stringMgr.getString("TableSelectionDiff.no.selection.err"));
         return;
      }

      if(1 == nbrSelRows && 1 == nbrSelCols)
      {
         Main.getApplication().getMessageHandler().showWarningMessage(s_stringMgr.getString("TableSelectionDiff.single.selection.warn"));
      }

      String markdown = CopyAsMarkDown.createMarkdownForSelectedCells(diffTableProvider.getTable());

      if(StringUtilities.isEmpty(markdown, true))
      {
         return;
      }

      Main.getApplication().getDBDiffState().storeSelectedForCompareMarkdown(markdown);
   }


   private static void onCompare(DiffTableProvider diffTableProvider)
   {
      Path leftMarkdownTempFile = Main.getApplication().getDBDiffState().getSelectedMarkdownTempFile();

      if(null == leftMarkdownTempFile)
      {
         Main.getApplication().getMessageHandler().showErrorMessage(s_stringMgr.getString("TableSelectionDiff.no.selection.for.compare.err"));
         return;
      }

      compareSelectedCellsToLeftTempFile(diffTableProvider, leftMarkdownTempFile);
   }

   private static void compareSelectedCellsToLeftTempFile(DiffTableProvider diffTableProvider, Path leftMarkdownTempFile)
   {
      int nbrSelRows = diffTableProvider.getTable().getSelectedRowCount();
      int nbrSelCols = diffTableProvider.getTable().getSelectedColumnCount();

      if(0 == nbrSelRows || 0 == nbrSelCols)
      {
         Main.getApplication().getMessageHandler().showErrorMessage(s_stringMgr.getString("TableSelectionDiff.no.selection.err"));
         return;
      }

      if(1 == nbrSelRows && 1 == nbrSelCols)
      {
         Main.getApplication().getMessageHandler().showWarningMessage(s_stringMgr.getString("TableSelectionDiff.single.selection.warn"));
      }

      String rightMarkdown = CopyAsMarkDown.createMarkdownForSelectedCells(diffTableProvider.getTable());
      Path rightMarkdownTempFile = TableSelectionDiffUtil.createRightTempFile(rightMarkdown);


      String diffDialogTitle = s_stringMgr.getString("TableSelectionDiff.dialog.title");
      DBDIffService.showDiff(leftMarkdownTempFile, rightMarkdownTempFile, diffDialogTitle);
   }

   private static void onCompareToClip(DiffTableProvider diffTableProvider)
   {
      String clipboardAsString = ClipboardUtil.getClipboardAsString();

      if(StringUtilities.isEmpty(clipboardAsString, true))
      {
         Main.getApplication().getMessageHandler().showWarningMessage(s_stringMgr.getString("TableSelectionDiff.clipboard.empty.warn"));
         return;
      }

      Path leftClipboardTempFile = TableSelectionDiffUtil.createLeftTempFile(clipboardAsString);

      compareSelectedCellsToLeftTempFile(diffTableProvider, leftClipboardTempFile);
   }
}
