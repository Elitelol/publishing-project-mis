import User from "./User";

export default interface UserComment {
    commentId: number | null
    user: User
    entityId: number
    posted: Date
    text: string
    rootCommentId: number | null
    replies: UserComment[]
}