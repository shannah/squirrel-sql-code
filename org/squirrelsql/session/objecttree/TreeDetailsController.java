package org.squirrelsql.session.objecttree;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.Node;
import javafx.scene.control.*;
import org.squirrelsql.aliases.dbconnector.DbConnectorResult;
import org.squirrelsql.drivers.DriversUtil;
import org.squirrelsql.drivers.SQLDriver;
import org.squirrelsql.services.I18n;

public class TreeDetailsController
{
   private TabPane _objectTreeDetailsTabPane = new TabPane();
   private I18n _i18n = new I18n(getClass());

   public TreeDetailsController(TreeView<ObjectTreeNode> objectsTree)
   {
      objectsTree.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<TreeItem<ObjectTreeNode>>()
      {
         @Override
         public void changed(ObservableValue<? extends TreeItem<ObjectTreeNode>> observable, TreeItem<ObjectTreeNode> oldValue, TreeItem<ObjectTreeNode> newValue)
         {
            onTreeNodeSelected(newValue);
         }
      });
   }

   private void onTreeNodeSelected(TreeItem<ObjectTreeNode> selectedItem)
   {
      _objectTreeDetailsTabPane.getTabs().clear();

      if(ObjectTreeNodeTypeKey.ALIAS_TYPE_KEY.equals(selectedItem.getValue().getTypeKey()))
      {

         Tab tab = new Tab(_i18n.t("objecttree.details.alias.metadata"));
         tab.setClosable(false);
         _objectTreeDetailsTabPane.getTabs().add(tab);


         DbConnectorResult dbConnectorResult = selectedItem.getValue().getDBConnectorResult();

         TableLoader tableLoader = new TableLoader();
         tableLoader.addColumn(_i18n.t("objecttree.details.alias.metadata.propertyName"));
         tableLoader.addColumn(_i18n.t("objecttree.details.alias.metadata.value"));

         SQLDriver driver = DriversUtil.findDriver(dbConnectorResult.getAlias().getDriverId());

         tableLoader.addRow("JDBC Driver CLASSNAME", driver.getDriverClassName());
         tableLoader.addRow("JDBC Driver CLASSPATH", DriversUtil.getJarFileNamesListString(driver));


         TableView tableMetadata = new TableView();
         tableLoader.load(tableMetadata);

         tab.setContent(tableMetadata);
      }

   }

   public Node getComponent()
   {
      return _objectTreeDetailsTabPane;
   }
}
