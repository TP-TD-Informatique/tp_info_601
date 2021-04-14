package model.enums

import java.io.Serializable

enum class NodeType : Serializable {
    PERSON,
    ACTOR,
    DIRECTOR,
    MOVIE,
    ;

    override fun toString() = name
}