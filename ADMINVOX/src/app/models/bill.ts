import { BillDetail } from "./billdetail";
import { User } from "./user";

export interface Bill {
    billID:number;
    user:User;
    billDescription:string;
    sumWeight:number;
    cost:number;
    billCreateDate:Date;
    billStatus:boolean;
    billPayDate:Date;
    image:string;
    billDetail: BillDetail[]
}