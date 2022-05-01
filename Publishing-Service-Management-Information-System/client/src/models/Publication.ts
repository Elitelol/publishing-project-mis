import Attachment from "./Attachment";
import Contract from "./Contract";
import Budget from "./Budget";
import Task from "./Task";

export default interface Publication {
    publicationId: number | null
    authorId: number[] | undefined[]
    name: string
    publicationType: string
    progressStatus: string
    rejectionReason: string
    isbn: string
    pageNumber: number | null
    language: string
    genre: string
    price: number | null
    publishDate: Date | null
    managerId: number | null
    attachments: Attachment[]
    contract: Contract | null
    budget: Budget | null
    tasks: Task[]
}