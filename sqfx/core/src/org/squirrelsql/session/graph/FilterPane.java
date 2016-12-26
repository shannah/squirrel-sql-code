package org.squirrelsql.session.graph;

import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import org.squirrelsql.Props;

public class FilterPane extends BorderPane
{
   private FilterPersistence _filterPersistence;

   public FilterPane(FilterPersistence filterPersistence)
   {
      _filterPersistence = filterPersistence;
      ImageView imageView = new ImageView(new Props(getClass()).getImage("filter.gif"));
      setCenter(imageView);
      addEventHandler(MouseEvent.MOUSE_PRESSED, e -> onFilterCtrl());
   }

   private void onFilterCtrl()
   {
      new FilterCtrl(_filterPersistence);
   }
}
