;**
;**	Logics for room 20 -- an old-fashioned debugger
;**
;**	LSL3:  Passionate Patti in Pursuit of the Pulsating Pectorals
;**	       Copyright 1989
;**		by Al Lowe, Bill Skirvin, Carlos Escobar, and Roger Hardy
;**
;**	Last Update:	October 11, 1989
;**


(include	"menu.sh")

(script# 	DEBUG)

(public
	rm20				0
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



(instance rm20 of Locale
	(method (handleEvent event &tmp i theObj xyWindow evt
						[string 30] [string1 30] [string2 30])

		(if (or (not debugging) (event claimed))
			(return)
		)


		(switch (event type?)
			(mouseDown
				(cond
					((& (event modifiers) shiftDown)
						(event claimed TRUE)
						(=	xyWindow
							(Print (Format @string "%d/%d" (event x) (event y))
								#at 150 100
								#font 999
								#dispose
							)
						)
						(while (!= mouseUp ((= evt	(Event new)) type))
							(evt dispose)
						)
						(xyWindow dispose)
						(evt dispose)
					)

					((& (event modifiers) ctrlDown)
						(event claimed TRUE)
						(User canControl	TRUE)
						(while (!= mouseUp ((= evt (Event new)) type))
							(GlobalToLocal evt)
							(ego posn (evt x) (evt y), setMotion 0)
							(RedrawCast)
							(evt dispose)
						)
						(evt dispose)
					)
				)
			)


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

					(ALT_E						;**	change Ego's view
						(if (Load VIEW (= i (GetNumber {New Ego View:})))
							(= normalEgoView i)
							(NormalEgo)
						)
					)

					(ALT_F						;**	Show Fragments
						(^= debugOn TRUE)
						(Print (Format @string
							"Debug will%spop up on next newRoom."
							(if debugOn { } else { NOT })))
					)

					(ALT_G						;**	Add Grid
						(if (Load PICTURE 999)
							(DrawPic 999 1 1)
						else
							(SetDebug)
						)
					)

					(ALT_I						;**	regain Input
						(User canInput TRUE)
					)

					(ALT_M						;**	show Memory usage
						(theGame showMem)
					)

					(ALT_P						;**	show Priority screen
						(Show PMAP)
					)

					(ALT_R						;**	show this Room's number
						(Print (Format @string
							"You are currently enjoying room %d." curRoomNum))
					)

					(ALT_S						;**	Sex change
						(if egoIsPatti
							(= egoIsPatti FALSE)
							(= normalEgoView vLarry)
							(= egoName (Format @egoNameBuffer "Larry"))
						else
							(= egoIsPatti TRUE)
							(= normalEgoView vPatti)
							(= egoName (Format @egoNameBuffer "Patti"))
						)
						(NormalEgo)
					)

					(ALT_T						;**	Time remaining
						(Print (Format @string "d:%2d" (/ rgTimer 600) (/ (mod rgTimer 600) 10)))
					)

					(ALT_V						;**	show Visual screen
						(Show VMAP)
					)

					(ALT_W						;**	Write ego's info
						(= string	0)
						(if (!= ERROR (GetInput @string 50 {Writing to \"ego.log\"}))
							(Format @string1 ";[r%3d " curRoomNum)
							(Format @string2
								"(ego view %d loop %d cel %d x %d y %d setPri %d)\r"
								(ego view) (ego loop) (ego cel)
								(ego x) (ego y) (ego priority)
							)
							(File
								name 	"ego.log",
								write @string1 @string "] " @string2 "\r",
								close
							)
						)
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

					(CTRL_D
						(if demo
							(= demo FALSE)
							(TheMenuBar draw)
							(StatusLine enable)
							(NormalEgo)
						else
							(= demo TRUE)
							(= i (GetNumber {Teleport to}))
							(if (Load SCRIPT i)
								(curRoom newRoom i)
							else
								(SetDebug)
							)
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

				(if (Said 'pitch>')
					(if (= theObj (inventory saidMe))
						(event claimed TRUE)
						(if (not (theObj ownedBy ego))
							(Print "You don't have it, Al.")
						else
							(Print "It's outta here, Al.")
							(theObj moveTo LIMBO)
						)
					)
				)

				(if (Said 'get>')
					(if (= theObj (inventory saidMe))
						(event claimed TRUE)
						(if (theObj ownedBy ego)
							(Print "You already have it, Al.")
						else
							(Print "You got it, Al.")
							(theObj moveTo ego)
						)
					)
				)
			)
		)

		(if (not (event claimed))
			(super handleEvent event)
		)
	)
)
