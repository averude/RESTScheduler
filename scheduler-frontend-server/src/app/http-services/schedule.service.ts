import { Injectable } from '@angular/core';
import { WorkDay } from '../model/workday';
import { Observable } from 'rxjs';
import { HttpClient } from '@angular/common/http';
import { RestConfig } from '../rest.config';
import { ScheduleDto } from "../model/dto/schedule-dto";
import { ScheduleGenerationDto } from "../model/dto/schedule-generation-dto";

@Injectable({
  providedIn: 'root'
})
export class ScheduleService {

  constructor(private http: HttpClient,
              private config: RestConfig) { }

  getAllByDate(from: string,
               to: string): Observable<ScheduleDto[]> {
    return this.http.get<ScheduleDto[]>(
      `${this.config.baseUrl}/schedule/dates?from=${from}&to=${to}`
    );
  }

  create(schedule: WorkDay[]): Observable<any> {
    return this.http.post<any>(
      `${this.config.baseUrl}/schedule`,
      schedule
    );
  }

  update(schedule: WorkDay[]): Observable<any> {
    return this.http.put(
      `${this.config.baseUrl}/schedule`,
      schedule,
      {responseType: 'text'}
    );
  }

  generate(scheduleGenerationDTO: ScheduleGenerationDto): Observable<any> {
    return this.http.post(
      `${this.config.baseUrl}/schedule/generate`,
      scheduleGenerationDTO,
      {responseType: 'text'}
    );
  }
}