package model.enums

import java.io.Serializable

enum class RelationType : Serializable {
    PLAYED_IN,
    DIRECTOR_OF,
    ;

    override fun toString() = name
}