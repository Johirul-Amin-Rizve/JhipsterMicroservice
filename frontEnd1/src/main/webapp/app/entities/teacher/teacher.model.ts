export interface ITeacher {
  id?: number;
  firstName?: string | null;
  lastName?: string | null;
  email?: string | null;
}

export class Teacher implements ITeacher {
  constructor(public id?: number, public firstName?: string | null, public lastName?: string | null, public email?: string | null) {}
}

export function getTeacherIdentifier(teacher: ITeacher): number | undefined {
  return teacher.id;
}
