package com.example.phase2;

import androidx.appcompat.app.AppCompatDialogFragment;

public class DialogFactory {

    public AppCompatDialogFragment getDialog(String dialogType){

        switch (dialogType){
            case "Approve":
                return new ApproveDialog();
            case "Freeze":
                return new FreezeDialog();
            case "Undo":
                return new UndoDialog();
            case "Unfreeze":
                return new UnFreezeDialog();
            default:
                return new AppCompatDialogFragment();
        }
    }

}
