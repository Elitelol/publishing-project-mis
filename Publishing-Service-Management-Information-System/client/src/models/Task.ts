import UserComment from "./UserComment";
import User from "./User";

export default interface Task {
    taskId: number | null
    publicationId: number | null
    responsiblePeople: User[]
    taskType: string
    taskName: string
    description: string
    startDate: Date | null
    dueDate: Date | null
    progress: string
    comments: UserComment[]
}