import { IDepartment } from 'app/entities/department/department.model';

export interface IStudent {
  id?: number;
  firstName?: string | null;
  lastName?: string | null;
  email?: string | null;
  department?: IDepartment | null;
}

export class Student implements IStudent {
  constructor(
    public id?: number,
    public firstName?: string | null,
    public lastName?: string | null,
    public email?: string | null,
    public department?: IDepartment | null
  ) {}
}

export function getStudentIdentifier(student: IStudent): number | undefined {
  return student.id;
}
