export default interface UserComment {
    commentId: number
    userId: number
    entityId: number
    posted: Date
    text: string
    rootCommentId: number
    replies: UserComment[]
}