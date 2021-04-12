package model.enums

import java.io.Serializable

enum class NodeType : Serializable {
    ACTOR,
    MOVIE,
    ;

    override fun toString() = name
}