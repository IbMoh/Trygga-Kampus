package com.example.tryggakampus.domain.repository


import com.google.firebase.storage.FirebaseStorage

class ArticlesPageRepository {

    // retrieve all PDF URLs from the "articles" folder in Firebase Storage
    fun fetchAllPdfUrls(onComplete: (List<String>) -> Unit) {
        val storageReference = FirebaseStorage.getInstance().reference.child("articles/")

        // List all files in the articles folder
        storageReference.listAll()
            .addOnSuccessListener { listResult ->
                val pdfUrls = mutableListOf<String>()

                // Fetch download URL for each file
                listResult.items.forEach { fileRef ->
                    fileRef.downloadUrl.addOnSuccessListener { uri ->
                        pdfUrls.add(uri.toString())
                        if (pdfUrls.size == listResult.items.size) {
                            onComplete(pdfUrls)
                        }
                    }.addOnFailureListener {
                        if (pdfUrls.size == listResult.items.size) {
                            onComplete(pdfUrls)
                        }
                    }
                }
            }
            .addOnFailureListener {
                onComplete(emptyList()) 
            }
    }
}
