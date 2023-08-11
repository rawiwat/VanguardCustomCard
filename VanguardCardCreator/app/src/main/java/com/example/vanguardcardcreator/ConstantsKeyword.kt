package com.example.vanguardcardcreator

import com.example.vanguardcardcreator.screens.SkillIcon

class Route {
    companion object {
        const val MAIN_MENU = "main"
        const val CREATE_UNIT = "create_unit?id={id}"
        const val CREATE_ORDER = "create_order?id={id}"
        const val CREATE_MARKER = "create_marker?id={id}"
        const val CREATE_NEW_CARD = "card_type_selection"
    }

    fun passUnitId(id: Int = 0): String {
        return "create_unit?id=$id"
    }

    fun passOrderId(id: Int = 0):String {
        return "create_order?id=$id"
    }

    fun passMakerId(id: Int = 0):String {
        return "create_marker?id=$id"
    }
}

class CardTextSymbol {
    companion object {
        const val POWER = "-power-"
        const val AUTO = "-auto-"
        const val VG_CIRCLE = "-vg-"
        //const val ALL_SYMBOL = "$POWER,$AUTO,$VG_CIRCLE"
    }
}

class Nation {
    companion object {
        const val BRANDT_GATE = "Brandt Gate"
        const val KETER = "Keter Sanctuary"
        const val DRAGON_EMPIRE = "Dragon Empire"
        const val DARK_STATES = "Dark States"
        const val STOICHEIA = "Stoicheia"
        const val LYRICAL_MONASTERIO = "Lyrical Monasterio"
    }
}

class CardType {
    companion object {
        const val UNIT = "Unit"
        const val ORDER = "Order"
        const val MARKER = "Marker"
    }
}

val IconResource = mutableListOf(
            SkillIcon("auto", R.drawable.auto_icon),
            SkillIcon("power", R.drawable.power_icon),
            SkillIcon("vg", R.drawable.vg_circle)
        )

class Label {
    companion object {
        const val BLITZ: String = "Blitz_Order"
        const val TRIGGER = "Trigger_Unit"
        const val NORMAL = "Normal_Unit"
        const val SET = "Set_Order"
        const val ORDER = "Normal_Order"
        const val TOKEN = "Token_Unit"
        const val G = "G_Unit"
    }
}

class Ability {
    companion object {
        const val BOOST = "Boost"
        const val INTERCEPT = "Intercept"
        const val TWIN_DRIVE = "Twin_Drive"
        const val TRIPLE_DRIVE = "Triple_drive"
    }
}