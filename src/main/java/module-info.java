module ch.kbw {
	requires transitive javafx.base;
    requires transitive javafx.controls;
    requires transitive javafx.graphics;
	requires transitive javafx.fxml;

    opens ch.kbw to javafx.fxml;
    exports ch.kbw;
}