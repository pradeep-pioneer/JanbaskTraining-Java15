package com.janbask.training3;
import java.beans.*;

/*
    Indexed Properties Example
    Author: Pradeep Singh
*/

public class BoundProperty implements java.io.Serializable {
    private int mMouthWidth = 90;
    private PropertyChangeSupport mPcs =
            new PropertyChangeSupport(this);

    public int getMouthWidth() {
        return mMouthWidth;
    }

    public void setMouthWidth(int mw) {
        int oldMouthWidth = mMouthWidth;
        mMouthWidth = mw;
        mPcs.firePropertyChange("mouthWidth",
                oldMouthWidth, mw);
    }

    public void addPropertyChangeListener(PropertyChangeListener listener) {
        mPcs.addPropertyChangeListener(listener);
    }

    public void removePropertyChangeListener(PropertyChangeListener listener) {
        mPcs.removePropertyChangeListener(listener);
    }
}
