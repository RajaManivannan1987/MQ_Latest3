package com.moneyquotient.in.in.sigleton;

import com.moneyquotient.in.in.Interface.ActionComplet;

/**
 * Created by Raja M on 8/29/2017.
 */

public class ActionOverSingleton {
    ActionComplet complet;
    private static ActionOverSingleton actionOverSingleton = new ActionOverSingleton();

    public static ActionOverSingleton getActionOverSingleton() {
        return actionOverSingleton;
    }

    public void setComplet(ActionComplet complet) {
        this.complet = complet;
    }

    public void ActionCompleted() {
        if (complet != null) {
            complet.complete();
        }
    }
}
