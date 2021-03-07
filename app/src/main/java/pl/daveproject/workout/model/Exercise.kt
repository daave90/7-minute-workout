package pl.daveproject.workout.model

class Exercise(
    private var id: Int,
    private var name: String,
    private var image: Int,
    private var completed: Boolean,
    private var selected: Boolean) {

    fun getId(): Int {
        return this.id
    }

    fun getName(): String {
        return this.name
    }

    fun getImage(): Int {
        return this.image
    }

    fun isCompleted(): Boolean {
        return this.completed
    }

    fun isSelected(): Boolean {
        return this.selected
    }

    fun setid(id: Int) {
        this.id = id
    }

    fun setName(name: String) {
        this.name = name
    }

    fun setImage(image: Int) {
        this.image = image
    }

    fun setCompleated(completed: Boolean) {
        this.completed = completed
    }

    fun setSelected(selected: Boolean) {
        this.selected = selected
    }
}