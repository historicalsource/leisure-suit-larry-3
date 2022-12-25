;**
;**	Logics for room 140 -- Quiz room
;**
;**	LSL3:  Passionate Patti in Pursuit of the Pulsating Pectorals
;**	       Copyright 1989
;**		by Al Lowe, Bill Skirvin, C. Escobar, and Roger Hardy
;**
;**	Last Update:	December 18, 1989
;**


(script#				140)


(public
	rm140				0
)


(procedure
	HighLight
	MouseInRect
	SetFlg140
	TstFlg140
	LoadSetup
	SaveSetup
	CLS
)


(define	vRoom							140)
	(define lFace					0)
	(define lSwimsuit				1)

(define	sCorrect						140)
(define	sInCorrect					141)

;(define	SANS_SERIF_10			3)


(define	FIRST_FILE					141)
(define	LAST_FILE					161)

(define	QUESTION_X					150)
(define	NUMBERS_X					150)
(define	ANSWERS_X					165)
(define	QUESTION_WIDTH				150)
(define	NUMBERS_WIDTH				 10)
(define	ANSWER_WIDTH				135)



(enum												;**	RoomScript state values
	intro
	askAquestion
	gotAnAnswer
	sweepUp
	youRbright
	changeRooms
)


(local
	tryCount
	atY
	correct
	suitCel
	question		= 0
	hisAnswer	= 0
	theAnswer
	thisFile
	theKey
	[filesSeen	99] 			;**	Handle 3*16 files, numbered 141-188
	[string	 300]
)


(instance rm140 of Room
	(properties
		picture		blackPicture		
	)


	(method (init)
		(Load PICTURE curRoomNum)

		(Load SOUND sCorrect)
		(Load SOUND sInCorrect)
		
		(Load FONT SERIF_9)

		(LoadSetup)
;**			(if debugging
;**				(Printf "These numbers came off the disk: %7d %7d %7d" 
;**								[filesSeen 0] [filesSeen 1] [filesSeen 2] )
;**			)

		(while (and	(< (++ tryCount) 1000) 
						(TstFlg140 (- (= thisFile (Random FIRST_FILE LAST_FILE)) FIRST_FILE)))
		)
		(if (>= tryCount 1000)
			(= [filesSeen 0] 0)
			(= [filesSeen 1] 0)
			(= [filesSeen 2] 0)
			(= thisFile (Random FIRST_FILE LAST_FILE))
		)
		(SetFlg140 (- thisFile FIRST_FILE))

		(SaveSetup)
;**			(if debugging
;**				(Printf "We've working file #%d.
;**					These numbers just went back to the disk: %7d %7d %7d" 
;**					thisFile			[filesSeen 0] [filesSeen 1] [filesSeen 2] )
;**			)

		(Load TEXT thisFile)

		(super init)

		(= demo 0)
;**			(ClrFlg noCursor)
;**			(NormalEgo)
		(HandsOn)
		(User
			canInput			FALSE,
		)
		(ego
			init,
			hide,
		)

		(self
			setScript 		RoomScript
		)
	)
)


(instance RoomScript of Script
	(method (handleEvent event &tmp evMod [str 33])
		(if (and (not (event claimed))
					(== state youRbright))
			(self cue)
		)

		(if (or	(event claimed) 
					(!= state askAquestion)
					(super handleEvent event))
			(return)
		)


		(switch (event type?)
			(mouseDown
				(cond
					((MouseInRect event 141  71 300  91)	
						(= hisAnswer 1)
						(self cue)
					)
					((MouseInRect event 141 101 300 121)	
						(= hisAnswer 2)
						(self cue)
					)
					((MouseInRect event 141 132 300 152)	
						(= hisAnswer 3)
						(self cue)
					)
					((MouseInRect event 141 161 300 186)	
						(= hisAnswer 4)
						(self cue)
					)
				)
			)

			(keyDown
				(= evMod (event modifiers?))
				(switch (event message?) 
					(`#2
						(event claimed)
						(TglSound)
					)
					(`a
						(= hisAnswer 1)
						(self cue)
					)
					(`b	
						(= hisAnswer 2)
						(self cue)
					)
					(`c	
						(= hisAnswer 3)
						(self cue)
					)
					(`d	
						(= hisAnswer 4)
						(self cue)
					)
					(`A
						(= hisAnswer 1)
						(self cue)
					)
					(`B	
						(= hisAnswer 2)
						(self cue)
					)
					(`C	
						(= hisAnswer 3)
						(self cue)
					)
					(`D	
						(= hisAnswer 4)
						(self cue)
					)
					(`@x 
						(if (& evMod ctrlDown)
							(event claimed: TRUE)
							(Print "CHEATER!!!\n\nOh well, O.K. 
								Since it's you, you may go ahead.
								Pick a filth level from 1-5,
								where 1 is \"clean\" and 5 is \"dirty.\"")
							(= correct 6)
							(while (u> correct 5)
								(= correct (GetNumber {from 1-5 ONLY!}))
							)
							(self changeState youRbright)
						)
					)
				)
			)
		)
	)


	(method (changeState newState &tmp age i filthFile [tmpString 200])
		(switch (= state newState)

			(intro
				(Print "Welcome to \"Leisure Suit Larry 3:  
					Passionate Patti in Pursuit of the Pulsating Pectorals!\"\n
					\n
					WARNING! This game contains some material which may be 
					deemed offensive by some players. 
					If you are offended by adult situations,
					vulgar language, ethnic humor, 
					sexual innuendo or pixelated nudity, 
					you'll be happier playing another game."
						#title		{Hello!___My name is Larry; Larry Laffer!}
						WIDE
				)
				(Display "Use the TAB key to select, then ENTER to continue."
					#p_at: 		99 166
					#p_color:	red
					#p_width:	130
					#p_font:		SERIF_9
				)
				(if (== 0	(Print "Oops, I don't wanna play\n
											no dirty computer games!\n\n
										  I'm gonna go watch PBS."
									#title		{Blush!}
									#button		{Bail\nOut} 0
									#button		{Oh, Go Ahead!\nTry to Offend Me!} 1
								))
					(= quit TRUE)
					(return)
				)
				(= age (Print "What is your age?"
							#title		{Reality Check}
							#button		{Under 12} 	-1
							#button		{13 to 17} 	0
							#button		{18 to 25} 	18
							#button		{over 25} 	25
					 	)
				)	
				(music fade)
				(curRoom drawPic blackPicture IRISOUT)
				(switch age
					(-1		
						(Print "Come back with your legal guardian!")
						(= quit TRUE)
					)
					(0					 	
						(Print "Welcome to the \"Clean\" version!")
						(self changeState changeRooms)
					)
					(else
						(curRoom drawPic curRoomNum IRISOUT)
						(aSuit			init)
						(addToPics
							add 			atpFace,
							doit,
						)
						(Format @string "To prove you are at least %d years old,
							just answer these five simple questions." age)
						(Print @string	LOW)
						(self cue)
					)
				)
			)

			(askAquestion
				;**	Do the question.
				(= atY 42)					
				(GetFarText thisFile (* question 5) @string)
				(= theAnswer (- (StrAt @string 0) 48))
				(for ((= i 0)) (<= i (StrLen @string)) ((++ i))
					(StrAt @tmpString i (StrAt @string (+ i 1)))
				)
				(Display	
					@tmpString
					#p_at: 		QUESTION_X atY
					#p_color:	blue
					#p_font:		SERIF_9
					#p_width:	QUESTION_WIDTH
				)

				;**	Do all four answers.
				(= atY 72)					
				(for ((= i 1)) (< i 5) ((++ i) (+= atY 30))
					;**	Display the number & a period...
					(Format @string "%c." (+ i 96))
					(Display
						@string
						#p_at: 		NUMBERS_X atY
						#p_color:	blue
						#p_font:		SERIF_9
						#p_width:	NUMBERS_WIDTH
					)

					;**	then display the text of the answer.
					(GetFarText thisFile (+ (* question 5) i) @tmpString)
					(Display
						@tmpString
						#p_at: 		ANSWERS_X atY
						#p_color:	blue
						#p_font:		SERIF_9
						#p_width:	ANSWER_WIDTH
					)
				)
			)

			(gotAnAnswer
				(if (== hisAnswer theAnswer)
					(HighLight hisAnswer green)
					(music
						number		sCorrect,
						loop			1,
						play,
					)
					(Print "Correct" 
						#at 		190 8 
						#time 	3 
						#dispose 
					)
					(++ correct)
					(++ suitCel)
				else
					(HighLight hisAnswer red)
					(music
						number		sInCorrect,
						loop			1,
						play,
					)
					(Print "Wrong" 
						#at 		190 8 
						#time 	3 
						#dispose 
					)
					(if suitCel
						(-- suitCel)
					)
				)
				(aSuit
					setCel			suitCel,
					forceUpd,
				)
				(delay 3)
			)

			(sweepUp								;**	Clear up text that's still there.
				(cls)
				(CLS)
				(= hisAnswer 0)
				(if ( < (++ question) 5)
					(= state (- askAquestion 1))
				)
				(= cycles 11)
			)	

			(youRbright
				(Format @string "You are so bright!\n\n
					You got %d correct out of 5 questions.
					Therefore, you get to play this game at the \"%s\" level!\n\n
					Have fun!!"
					correct
					(switch correct
						(5		{Totally Raunchiest})
						(4		{Really Filthy})
						(3		{Pretty Dirty})
						(2		{Rather Risque})
						(else	{Mother Goose})
					)
				)
				(CLS)
				(Display @string
					#p_at: 		160 60
					#p_color:	blue
					#p_font:		userFont
					#p_width:	130
				)
				(delay 9)
			)

			(changeRooms
				(if (== correct 0)
					(++ correct)
				)
				(= filthLevel (- correct 1))
				(= filthFile (FOpen {RESOURCE.LL3} fTrunc))
				(if (!= filthFile ERROR) 
					(Format @tmpString "%7d" filthLevel)
					(FPuts filthFile @tmpString)
				)
				(FClose filthFile)
				(curRoom newRoom 290)
			)
		)
	)
)


(instance atpFace of PicView
	(properties
		view				vRoom
		loop				lFace
		x	 				50
		y					52
		priority			15
	)
)
(instance aSuit of Prop
	(properties
		view				vRoom
		loop				lSwimsuit
		cel				0
		x					83 
		y					77
	)
)


(procedure (HighLight i c &tmp [tmpString 200])
	(= atY (+ 42 (* i 30)))
	(Format @string "%c." (+ i 96))
	(Display
		@string
		#p_at: 		NUMBERS_X atY
		#p_color:	c
		#p_font:		SERIF_9
		#p_width:	NUMBERS_WIDTH
	)

	(GetFarText thisFile (+ (* question 5) i) @tmpString)
	(Display
		@tmpString
		#p_at:		ANSWERS_X atY
		#p_color:	c
		#p_font:		SERIF_9
		#p_width:	ANSWER_WIDTH
	)
)

(procedure (MouseInRect event left top right bottom &tmp evX evY)
	(= evX (event x?))
	(= evY (event y?))
	(return (if (and	( > evX left)
							( > evY top)
							( < evX right)
							( < evY bottom))
			TRUE
		else
			FALSE
		)
	)
)

(procedure (SetFlg140 flag)
	(|= [filesSeen (/ flag 16)] (>> $8000 (mod flag 16)))
)
(procedure (TstFlg140 flag)
	(return (if (& [filesSeen (/ flag 16)] (>> $8000 (mod flag 16))) TRUE else FALSE))
)

(procedure (LoadSetup &tmp questionFile soundState [str 30])
	(= questionFile (FOpen {LARRY3.DRV} fRead))
	(if (!= questionFile ERROR) 
		(= [filesSeen 0] (ReadNumber (FGets @str 8 questionFile)))
		(= [filesSeen 1] (ReadNumber (FGets @str 8 questionFile)))
		(= [filesSeen 2] (ReadNumber (FGets @str 8 questionFile)))
	)
	(FClose questionFile)
)
(procedure (SaveSetup &tmp [str 40] questionFile)
	(= questionFile (FOpen {LARRY3.DRV} fTrunc))
	(if (!= questionFile ERROR) 
		(Format @str "%6d %6d %6d %5s" [filesSeen 0] [filesSeen 1] [filesSeen 2] " ")
		(FPuts questionFile @str)
	)
	(FClose questionFile)
)
(procedure (CLS)
	(Graph GFillRect 32 150 189 302 VMAP ltWhite)
	(Graph GShowBits 32 150 189 302 VMAP)
)
