import Attachment from "./Attachment";
import Contract from "./Contract";
import Budget from "./Budget";
import Task from "./Task";

export default interface Publication {
    publicationId: number
    authorId: number[]
    name: string
    publicationType: string
    progressStatus: string
    rejectionReason: string
    isbn: string
    pageNumber: number
    language: string
    genre: string
    price: string
    publishDate: Date
    managerId: number
    attachments: Attachment[]
    contract: Contract
    budget: Budget
    tasks: Task[]
}