package model.enums

import java.io.Serializable

enum class RelationType : Serializable {
    IS_A,
    A_KIND_OF,
    PLAYED_IN,
    DIRECTOR_OF,
    ;

    override fun toString() = name
}