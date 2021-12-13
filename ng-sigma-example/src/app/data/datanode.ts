import { Income } from "./income";

export interface DataNode {
    id: string,
    parentId: string,
    player: string,
    turn: string,
    round: number,
    gameIsFinished: boolean,
    incomes: Income[]
}