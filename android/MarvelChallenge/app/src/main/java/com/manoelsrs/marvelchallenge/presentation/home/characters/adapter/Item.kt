package com.manoelsrs.marvelchallenge.presentation.home.characters.adapter

import com.manoelsrs.marvelchallenge.model.Character

class Item(item: Character) : ItemViewModel {
    override val raw: Any = item
    override val id: Int = item.id
    override val name: String = item.name
    override val photo: String = item.photo
    override val photoExtension: String = item.photoExtension
}