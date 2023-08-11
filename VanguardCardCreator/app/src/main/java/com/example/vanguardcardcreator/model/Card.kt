package com.example.vanguardcardcreator.model

open class Card (
    open val id: Int = 0,
    open val name: String = "",
    open val text: String = "",
    open val type: String = "",
    open val image: String = "",
    open val nation: String = "",
    open val grade: Int = 0,
    open val clan: String = "",
    open val race:String = "",
    open val label:String = "",
    open val power: String = "",
    open val shield: String = "",
    open val trigger: String = "",
    open val personaRide: Boolean = false,
    open val regalis:Boolean = false,
    open val ability: String = ""
)

class Unit(
    override val id: Int,
    override val name: String,
    override val text: String,
    override val type: String,
    override val image: String,
    override val nation: String,
    override val race:String,
    override val label:String,
    override val power: String,
    override val shield: String,
    override val trigger: String,
    override val personaRide: Boolean,
    override val ability: String
    ) : Card()

class Order(
    override val id: Int,
    override val name: String,
    override val text: String,
    override val type: String,
    override val image: String,
    override val nation: String,
    override val clan: String,
    override val grade: Int,
    override val label: String,
    override val regalis: Boolean,
    override val race: String
): Card()

class Marker(
    override val id: Int,
    override val name: String,
    override val type: String,
    override val text: String,
    override val image: String,
): Card()