package com.example.qcassistantmaven.domain.order;

import com.example.qcassistantmaven.domain.item.document.Document;
import com.example.qcassistantmaven.regex.MedidataOrderInputRegex;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class DocumentRepository {

    private Collection<Document> documents;

    public DocumentRepository(){
        this.documents = new ArrayList<>();
    }

    public Collection<Document> getDocuments() {
        return documents;
    }

    public DocumentRepository setDocuments(List<Document> documents) {
        this.documents = documents;
        return this;
    }

    public void addDocument(Document document){
        this.documents.add(document);
    }

    public boolean areMultipleCopiesRequested() {
        for(Document document : documents){
            if(document.getCopiesCount() > 1){
                return true;
            }
        }

        return false;
    }

    public boolean areUserGuidesRequested() {
        for(Document document : documents){
            if(!document.getShortName().equals(
                    MedidataOrderInputRegex.WELCOME_LETTER_SHORTNAME)){
                return true;
            }
        }

        return false;
    }
}
