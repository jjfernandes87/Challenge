package com.manoelsrs.marvelchallenge.presentation.home.characters.adapter

interface ItemViewModel {
    val raw: Any
    val id: Int
    val name: String
    val photo: String
    val photoExtension: String
}