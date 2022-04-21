import { IStudent } from 'app/entities/student/student.model';

export interface IDepartment {
  id?: number;
  name?: string | null;
  address?: string | null;
  students?: IStudent[] | null;
}

export class Department implements IDepartment {
  constructor(public id?: number, public name?: string | null, public address?: string | null, public students?: IStudent[] | null) {}
}

export function getDepartmentIdentifier(department: IDepartment): number | undefined {
  return department.id;
}
