package org.cyberpwn.react.ui;

import javax.swing.*;
import java.awt.*;

interface ITabRenderer {
    Component getTabRendererComponent(JTabbedPane tabbedPane, String text, Icon icon, int tabIndex);
}