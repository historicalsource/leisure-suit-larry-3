;**
;**	Logics for room 22 -- QA helper
;**
;**	LSL3:  Passionate Patti in Pursuit of the Pulsating Pectorals
;**	       Copyright 1989
;**		by Al Lowe, Bill Skirvin, Carlos Escobar, and Roger Hardy
;**
;**	Last Update:	October 24, 1989
;**


(include	"menu.sh")

(script# 	QA)

(public
	rm22				0
)


(define	ALT_C		`@C)					;**	shows Control screen
(define	ALT_D		`@D)					;**	is same as ShiftShift-
(define	ALT_E		`@E)					;**	changes Ego's view
(define	ALT_F		`@F)					;**	to see Fragments
(define	ALT_G		`@G)					;**	overlays a Grid
(define	ALT_I		`@I)					;**	to regain Input
(define	ALT_M		`@M)					;**	shows Memory usage
(define	ALT_N		`@N)					;**	to make a Note
(define	ALT_P		`@P)					;**	shows Priority screen
(define	ALT_R		`@R)					;**	shows this Room's number
(define	ALT_S		`@S)					;**	instant Sex change
(define	ALT_T		`@T)					;**	Time left on timer
(define	ALT_V		`@V)					;**	shows Visual screen
(define	ALT_W		`@W)					;**	Writes ego's info
(define	ALT_X		`@X)					;**	in honor of Skirvin
(define	ALT_Z		`@Z)					;**	QuickQuit\05
(define	CTRL_D	`^D)					;**	to turn off Demo
(define	CTRL_E	`^E)					;**	shows Ego data
(define	CTRL_H	`^H)					;**	shows Heap (memory)
(define	CTRL_T	`^T)					;**	teleport



(instance rm22 of Locale
	(method (handleEvent event &tmp i theObj xyWindow evt
						[string 30] [string1 30] [string2 30])

		(if (or (not (TstFlg inQA)) (event claimed))
			(return)
		)


		(switch (event type?)
			(keyDown
				(switch (event message?)
					(ALT_C						;**	show Control screen
						(Show CMAP)
						(RedrawCast)
						(while (== 0 ((= event (Event new)) type))
							(event dispose))
						(event dispose)
						(Show VMAP)
						(return)
					)

					(ALT_D						;**	SCI Debugger
						(SetDebug)
					)

					(ALT_I						;**	regain Input
						(User canInput TRUE)
					)

					(ALT_M						;**	show Memory usage
						(theGame showMem)
					)

					(ALT_N						;**	make a Note
						(= string1	0)
						(= string2	0)
						(= i 1)
						(++ noteCounter)
						(while (GetInput @string2 40 {Press \"Enter\" or \"ESC\" when done.})
							(Format @string "Note%s.%s" (theGame name) @initialsBuffer)
							(Format @string1 ";[%3d %s %6s %3s %3d %2d %3d %3d/%3d] "
   												curRoomNum version @dateBuffer 
													@initialsBuffer noteCounter i   
													(ego view) (ego x) (ego y)
							)
							(File name @string, write @string1 @string2 "\r", close)
							(= string2 0)
							(++ i)
						)
					)

					(ALT_P						;**	show Priority screen
						(Show PMAP)
					)

					(ALT_R						;**	show this Room's number
						(Print (Format @string
							"You are currently enthusiastically enjoying room %d." 
							curRoomNum))
					)

					(ALT_V						;**	show Visual screen
						(Show VMAP)
					)

					(ALT_X						;**	in honor of Brief
						(= quit 1)
					)

					(ALT_Z						;**	quickQuit\05
						(= quit 1)
					)

					(CTRL_T
						(= i (GetNumber {Teleport to}))
						(if (Load SCRIPT i)
							(NormalEgo)
							(curRoom newRoom i)
						else
							(Print "What room was that supposed to be?")
							(SetDebug)
						)
					)

					(CTRL_E						;**	show Ego's info
						(Print (Format @string {view %d loop %d cel %d posn %d %d
								pri %d OnControl $%x Origin on $%x}
								(ego view) (ego loop) (ego cel) (ego x) (ego y)
								(ego priority) (ego onControl) (ego onControl origin))
							#icon (ego view) (ego loop) (ego cel))
					)

					(CTRL_H						;**	show Heap (memory)
						(theGame showMem)
					)
				)
			)


			(saidEvent
				(if (Said 'tp')
					(= i (GetNumber {Teleport to}))
					(if (Load SCRIPT i)
						(NormalEgo)
						(curRoom newRoom i)
					else
						(Print "What room was that supposed to be?")
						(SetDebug)
					)
				)
			)
		)

		(if (not (event claimed))
			(super handleEvent event)
		)
	)
)
