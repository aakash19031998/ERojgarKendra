package com.twocoms.rojgarkendra.documentsscreen.view;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.provider.Settings;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;
import com.twocoms.rojgarkendra.R;
import com.twocoms.rojgarkendra.documentsscreen.controler.AddDocActivity;
import com.twocoms.rojgarkendra.documentsscreen.controler.MyDocumentsActivity;
import com.twocoms.rojgarkendra.documentsscreen.controler.ViewPdfActivity;
import com.twocoms.rojgarkendra.documentsscreen.model.DocumentsModel;
import com.twocoms.rojgarkendra.global.model.AppConstant;
import com.twocoms.rojgarkendra.global.model.CommonMethod;
import com.twocoms.rojgarkendra.global.model.GlobalPreferenceManager;
import com.twocoms.rojgarkendra.jobboardscreen.controler.FrgAllJobs;
import com.twocoms.rojgarkendra.jobboardscreen.controler.JobDetailActivity;
import com.twocoms.rojgarkendra.jobboardscreen.model.ModelHotJobs;
import com.twocoms.rojgarkendra.registrationscreen.controler.RegisterUserDataActivity;

import java.util.ArrayList;
import java.util.List;

public class DocumentListAdapter extends RecyclerView.Adapter<DocumentListAdapter.ViewHolder> {

    public Context context;
    public ArrayList<DocumentsModel> allDocuments;
    private AlertDialog dialog;

    public DocumentListAdapter(Context context, ArrayList<DocumentsModel> allDocuments) {
        this.context = context;
        this.allDocuments = allDocuments;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View normalView = LayoutInflater.from(context).inflate(R.layout.item_document, null);
        return new ViewHolder(normalView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {

        final DocumentsModel documentsModel = allDocuments.get(position);
        holder.viewDocument.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(documentsModel.paySlipUrl));
                context.startActivity(browserIntent);
            }
        });


        holder.addDocument.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!documentsModel.approvedStatus.equals("yes")) {
                    MyDocumentsActivity myDocumentsActivity = (MyDocumentsActivity) context;
                    Dexter.withActivity(myDocumentsActivity)
                            .withPermissions(Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                            .withListener(new MultiplePermissionsListener() {
                                @Override
                                public void onPermissionsChecked(MultiplePermissionsReport report) {
                                    MyDocumentsActivity documentsActivity = (MyDocumentsActivity) context;

                                    if (report.areAllPermissionsGranted()) {
                                        documentsActivity.callAddDocActivity(documentsModel.paySlipMonth, documentsModel.paySlipYear);
                                    }

                                    if (report.isAnyPermissionPermanentlyDenied()) {
                                        documentsActivity.showSettingsDialog();
                                    }
                                }

                                @Override
                                public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {
                                    token.continuePermissionRequest();
                                }
                            }).check();


                }
                else {
                    CommonMethod.showToast("Document already verifed no need to upload again",context);
                }
            }
        });


        holder.documentName.setText(documentsModel.paySlipMonth + " " + documentsModel.paySlipYear);
        if (documentsModel.approvedStatus.equals("yes")) {
            holder.verifiedDocument.setVisibility(View.VISIBLE);
            holder.addDocument.setVisibility(View.GONE);
        } else {
            holder.verifiedDocument.setVisibility(View.GONE);
            holder.addDocument.setVisibility(View.VISIBLE);
        }


        if (!documentsModel.paySlipUrl.equals("") && !documentsModel.paySlipUrl.equals("null")) {
            holder.viewDocument.setVisibility(View.VISIBLE);
        } else {
            holder.viewDocument.setVisibility(View.GONE);
        }

    }


    @Override
    public int getItemCount() {
        return allDocuments.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView documentName;
        ImageView viewDocument, verifiedDocument, addDocument;

        ViewHolder(View itemView) {
            super(itemView);
            viewDocument = itemView.findViewById(R.id.viewDocument);
            verifiedDocument = itemView.findViewById(R.id.verifiedDocument);
            addDocument = itemView.findViewById(R.id.addDocument);
            documentName = itemView.findViewById(R.id.documentName);
        }

    }







}
