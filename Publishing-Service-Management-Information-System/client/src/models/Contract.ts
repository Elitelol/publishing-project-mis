import UserComment from "./UserComment";

export default interface Contract {
    publicationId: number
    contractId: number
    publishDate: Date
    publicationPrice: number
    amountOnSigningContract: number
    amountOfCompletedManuscript: number
    amountOnInitialPublish: number
    withinMonthsAfterPublish: number
    firstCoverRate: number
    firstCoverPercent: number
    secondCoverRate: number
    secondCoverPercent: number
    lastCoverRate: number
    lastCoverPercent: number
    comments: UserComment[]
}