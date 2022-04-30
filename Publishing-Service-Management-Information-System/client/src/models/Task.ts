import UserComment from "./UserComment";

export default interface Task {
    taskId: number
    publicationId: number
    responsiblePeopleIds: number[]
    taskType: string
    taskName: string
    description: string
    startDate: Date
    dueDate: Date
    progress: string
    comments: UserComment[]
}