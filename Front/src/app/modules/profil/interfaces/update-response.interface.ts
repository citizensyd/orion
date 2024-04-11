import {SubscriptionDTO} from "../../article/interfaces/subscription.interface";

export interface UpdateResponse {
  id: number;
  username: string;
  email: string;
  subscriptions: SubscriptionDTO[];
  message?: string;
}
