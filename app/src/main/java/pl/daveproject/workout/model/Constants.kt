package pl.daveproject.workout.model

import pl.daveproject.workout.R

class Constants {
    companion object {
        fun createDefaultExerciseList(): ArrayList<Exercise> {
            val exercises = ArrayList<Exercise>()
            val jumpingJacks =
                Exercise(1, "Jumping Jacks", R.drawable.ic_jumping_jacks, false, false)
            exercises.add(jumpingJacks)

            val wallSit =
                Exercise(2, "Wall Sit", R.drawable.ic_wall_sit, false, false)
            exercises.add(wallSit)

            val pushUp =
                Exercise(3, "Push Up", R.drawable.ic_push_up, false, false)
            exercises.add(pushUp)

            val abdominalCrunch =
                Exercise(4, "Abdominal Crunch", R.drawable.ic_abdominal_crunch, false, false)
            exercises.add(abdominalCrunch)

            val stepUpOnChair =
                Exercise(5, "Step-Up onto Chair", R.drawable.ic_step_up_onto_chair, false, false)
            exercises.add(stepUpOnChair)

            val squat =
                Exercise(6, "Squat", R.drawable.ic_squat, false, false)
            exercises.add(squat)

            val tricepDipOnChair =
                Exercise(7, "Tricep Dip On Chair", R.drawable.ic_triceps_dip_on_chair, false, false)
            exercises.add(tricepDipOnChair)

            val plank =
                Exercise(8, "Plank", R.drawable.ic_plank, false, false)
            exercises.add(plank)

            val highKneesRunningInPlace =
                Exercise(9, "High Knees Running In Place", R.drawable.ic_high_knees_running_in_place, false, false)
            exercises.add(highKneesRunningInPlace)

            val lunges =
                Exercise(10, "Lunges", R.drawable.ic_lunge, false, false)
            exercises.add(lunges)

            val pushupAndRotation =
                Exercise(11, "Push up and Rotation", R.drawable.ic_push_up_and_rotation, false, false)
            exercises.add(pushupAndRotation)

            val sidePlank =
                Exercise(12, "Side Plank", R.drawable.ic_side_plank, false, false)
            exercises.add(sidePlank)
            return exercises
        }
    }
}