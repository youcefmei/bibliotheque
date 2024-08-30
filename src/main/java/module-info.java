module com.youcefmei.bibliotheque {
    requires javafx.fxml;
    requires javafx.controls;
    requires com.formdev.flatlaf;
    requires transitive javafx.base;
    requires transitive javafx.graphics;
    requires static lombok;
    requires java.desktop;
    requires org.apache.commons.lang3;
    requires org.apache.commons.validator;
//    opens com.youcefmei.bibliotheque to javafx.fxml,formdev.flatlaf;
    opens com.youcefmei.bibliotheque.views.javafx.controllers to javafx.fxml;
    opens com.youcefmei.bibliotheque.models to javafx.base;
    exports com.youcefmei.bibliotheque;
    exports com.youcefmei.bibliotheque.views.javafx.controllers;
//    exports com.youcefmei.controllers;

}