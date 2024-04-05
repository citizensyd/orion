export interface ArticleDTO {
  id: number;
  createdAt: Date;
  title: string;
  content: string;
  themeId: number;
  userId: number;
  userName: string;
}
