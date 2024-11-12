package com.cioli.dogstuff

fun String.capitalizeWords(): String {
	return split(" ").joinToString(" ") { word ->
		word.replaceFirstChar { it.titlecaseChar() }
	}
}