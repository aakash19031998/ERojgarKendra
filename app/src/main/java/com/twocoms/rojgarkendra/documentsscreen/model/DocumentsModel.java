package com.twocoms.rojgarkendra.documentsscreen.model;

import android.os.Parcel;
import android.os.Parcelable;

public class DocumentsModel implements Parcelable {

    public String id  = "";
    public String candidateId  ="";
    public String paySlipUrl = "";
    public String uploadDate = "";
    public String approvedStatus = "";
    public String verifiedBy  = "" ;
    public String verifiedDate  = "";
    public String paySlipYear = "";
    public String paySlipMonth = "";


    public DocumentsModel(Parcel in) {
        id = in.readString();
        candidateId = in.readString();
        paySlipUrl = in.readString();
        uploadDate = in.readString();
        approvedStatus = in.readString();
        verifiedBy = in.readString();
        verifiedDate = in.readString();
        paySlipYear = in.readString();
        paySlipMonth = in.readString();
    }

    public DocumentsModel() {

    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(candidateId);
        dest.writeString(paySlipUrl);
        dest.writeString(uploadDate);
        dest.writeString(approvedStatus);
        dest.writeString(verifiedBy);
        dest.writeString(verifiedDate);
        dest.writeString(paySlipYear);
        dest.writeString(paySlipMonth);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<DocumentsModel> CREATOR = new Creator<DocumentsModel>() {
        @Override
        public DocumentsModel createFromParcel(Parcel in) {
            return new DocumentsModel(in);
        }

        @Override
        public DocumentsModel[] newArray(int size) {
            return new DocumentsModel[size];
        }
    };
}
